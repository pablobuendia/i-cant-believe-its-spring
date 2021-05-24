'use strict';

// tag::vars[]
const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
const client = require('./client'); // <3>
const when = require('when');
const follow = require('./follow'); // function to hop multiple links by "rel"

const root = '/api';
// end::vars[]

// tag::app[]
class App extends React.Component { // <1>

	constructor(props) {
		super(props);
		this.state = {administratives: [], attributes: [], pageSize: 2, links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
	}

	componentDidMount() { // <2>
		client({method: 'GET', path: '/api/administratives'}).done(response => {
			this.setState({administratives: response.entity._embedded.administratives});
		});
	}

	// tag::create[]
	onCreate(newAdministrative) {
		const self = this;
		follow(client, root, ['administratives']).then(administrativeCollection => {
			return client({
				method: 'POST',
				path: administrativeCollection.entity._links.self.href,
				entity: newAdministrative,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [
				{rel: 'administratives', params: {'size': this.state.pageSize}}]);
		}).done(response => {
			if (typeof response.entity._links.last !== "undefined") {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		});
	}
	// end::create[]

	// tag::update[]
	onUpdate(administrative, updatedAdministrative) {
		client({
			method: 'PUT',
			path: administrative.entity._links.self.href,
			entity: updatedAdministrative,
			headers: {
				'Content-Type': 'application/json',
				'If-Match': administrative.headers.Etag
			}
		}).done(response => {
			this.loadFromServer(this.state.pageSize);
		}, response => {
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' +
					administrative.entity._links.self.href + '. Your copy is stale.');
			}
		});
	}
	// end::update[]

	// tag::delete[]
	onDelete(administrative) {
		client({method: 'DELETE', path: administrative._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}
	// end::delete[]	

	// tag::navigate[]
	onNavigate(navUri) {
		client({method: 'GET', path: navUri}).then(administrativeCollection => {
			this.links = administrativeCollection.entity._links;

			return administrativeCollection.entity._embedded.administratives.map(administrative =>
					client({
						method: 'GET',
						path: administrative._links.self.href
					})
			);
		}).then(administrativePromises => {
			return when.all(administrativePromises);
		}).done(administratives => {
			this.setState({
				administratives: administratives,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}
	// end::navigate[]

	// tag::update-page-size[]
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}
	// end::update-page-size[]

	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<AdministrativeList administratives={this.state.administratives}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  attributes={this.state.attributes}
							  onNavigate={this.onNavigate}
							  onUpdate={this.onUpdate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>
		)
	}
}
// end::app[]

// tag::create-dialog[]
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) { // stops the event from bubbling further up the hierarchy
		e.preventDefault(); // ?? posibbly related with the comment above
		const newAdministrative = {}; 
		this.props.attributes.forEach(attribute => {
			newAdministrative[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim(); // It then uses the same JSON Schema attribute property to find each <input>
		});
		this.props.onCreate(newAdministrative); // we invoke a callback to onCreate() for the new administrative record

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

	render() {
		// "key" is again needed by React to distinguish between multiple child nodes. "placeholder" lets us show the user with field is which.
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}> 
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);

		return ( // That button has an onClick={this.handleSubmit} event handler. This is the React way of registering an event handler.
			<div>
				<a href="#createAdministrative">Create</a>

				<div id="createAdministrative" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new administrative</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}

class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const updatedAdministrative = {};
		this.props.attributes.forEach(attribute => {
			updatedAdministrative[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onUpdate(this.props.administrative, updatedAdministrative);
		window.location = "#";
	}

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={this.props.administrative.entity[attribute]}>
				<input type="text" placeholder={attribute}
					   defaultValue={this.props.administrative.entity[attribute]}
					   ref={attribute} className="field"/>
			</p>
		);

		const dialogId = "updateAdmnistrative-" + this.props.administrative.entity._links.self.href;

		return (
			<div key={this.props.administrative.entity._links.self.href}>
				<a href={"#" + dialogId}>Update</a>
				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Update an administrative</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Update</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

};

// tag::administrative-list[]
class AdministrativeList extends React.Component{
	render() {
		const administratives = this.props.administratives.map(administrative =>
			<Administrative key={administrative._links.self.href} administrative={administrative}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Document Number</th>
					</tr>
					{administratives}
				</tbody>
			</table>
		)
	}
}
// end::administrative-list[]

// tag::administrative[]
class Administrative extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.administrative.firstName}</td>
				<td>{this.props.administrative.lastName}</td>
				<td>{this.props.administrative.documentNumber}</td>
			</tr>
		)
	}
}
// end::administrative[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)