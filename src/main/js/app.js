'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom'); 
const client = require('./client');
const when = require('when');

const follow = require('./follow'); // function to hop multiple links by "rel"

const stompClient = require('./websocket-listener');

const root = '/api';
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {administratives: [], attributes: [], page: 1, pageSize: 2, links: {}, loggedInBoss: this.props.loggedInBoss};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
		this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
		this.refreshAndGoToLastPage = this.refreshAndGoToLastPage.bind(this);
	}

	// loadFromServer(pageSize) {
	// 	follow(client, root, [
	// 		{rel: 'administratives', params: {size: pageSize}}]
	// 	).then(administrativeCollection => { // It creates a call to fetch JSON Schema data.
	// 		return client({
	// 			method: 'GET',
	// 			path: administrativeCollection.entity._links.profile.href,
	// 			headers: {'Accept': 'application/schema+json'}
	// 		}).then(schema => { 
	// 			this.schema = schema.entity; // This has an inner then clause to store the metadata and navigational links in the <App/> component.
	// 			this.links = administrativeCollection.entity._links;
	// 			return administrativeCollection; // this embedded promise returns the administrativeCollection
	// 		});
	// 	}).then(administrativeCollection => { // The second then(administrativeCollection ⇒ …​) clause converts the collection of administratives into an array of GET promises to fetch each individual resource.
	// 		return administrativeCollection.entity._embedded.administratives.map(administrative =>
	// 				client({
	// 					method: 'GET',
	// 					path: administrative._links.self.href
	// 				})
	// 		);
	// 	}).then(administrativePromises => { // The then(administrativePromises ⇒ …​) clause takes the array of GET promises and merges them into a single promise with when.all()
	// 		return when.all(administrativePromises); // which is resolved when all the GET promises are resolved.
	// 	}).done(administratives => { // loadFromServer wraps up with done(administratives ⇒ …​) where the UI state is updated using this amalgamation of data.
	// 		this.setState({
	// 			administratives: administratives,
	// 			attributes: Object.keys(this.schema.properties),
	// 			pageSize: pageSize,
	// 			links: this.links
	// 		});
	// 	});
	// }

	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'administratives', params: {size: pageSize}}]
		).then(administrativeCollection => { // It creates a call to fetch JSON Schema data.
			return client({
				method: 'GET',
				path: administrativeCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => { 
				// tag::json-schema-filter[]
				/**
				 * Filter unneeded JSON Schema properties, like uri references and
				 * subtypes ($ref).
				 */
				 Object.keys(schema.entity.properties).forEach(function (property) {
					if (schema.entity.properties[property].hasOwnProperty('format') &&
						schema.entity.properties[property].format === 'uri') {
						delete schema.entity.properties[property];
					}
					else if (schema.entity.properties[property].hasOwnProperty('$ref')) {
						delete schema.entity.properties[property];
					}
				});

				this.schema = schema.entity; // This has an inner then clause to store the metadata and navigational links in the <App/> component.
				this.links = administrativeCollection.entity._links;
				return administrativeCollection; // this embedded promise returns the administrativeCollection
			});
		}).then(administrativeCollection => { // The second then(administrativeCollection ⇒ …​) clause converts the collection of administratives into an array of GET promises to fetch each individual resource.
			this.page = administrativeCollection.entity.page;
			return administrativeCollection.entity._embedded.administratives.map(administrative =>
					client({
						method: 'GET',
						path: administrative._links.self.href
					})
			);
		}).then(administrativePromises => { // The then(administrativePromises ⇒ …​) clause takes the array of GET promises and merges them into a single promise with when.all()
			return when.all(administrativePromises); // which is resolved when all the GET promises are resolved.
		}).done(administratives => { // loadFromServer wraps up with done(administratives ⇒ …​) where the UI state is updated using this amalgamation of data.
			this.setState({
				page: this.page,
				administratives: administratives,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
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
		});
	}
	// end::create[]

	// tag::update[]
	onUpdate(administrative, updatedAdministrative) {
		if(administrative.entity.boss.name === this.state.loggedInBoss) {
			updatedAdministrative["boss"] = administrative.entity.boss;
			client({
				method: 'PUT',
				path: administrative.entity._links.self.href,
				entity: updatedAdministrative,
				headers: {
					'Content-Type': 'application/json',
					'If-Match': administrative.headers.Etag
				}
			}).done(response => {
				/* Let the websocket handler update the state */
			}, response => {
				if (response.status.code === 403) {
					alert('ACCESS DENIED: You are not authorized to update ' +
						administrative.entity._links.self.href);
				}				
				if (response.status.code === 412) {
					alert('DENIED: Unable to update ' +
						administrative.entity._links.self.href + '. Your copy is stale.');
				}
			});
		} else {
			alert("You are not authorized to update");
		}


	}
	// end::update[]

	// tag::delete[]
	onDelete(administrative) {
		client({method: 'DELETE', path: administrative._links.self.href});
	}
	// end::delete[]	

	// tag::navigate[]
	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(administrativeCollection => {
			this.links = administrativeCollection.entity._links;
			this.page = administrativeCollection.entity.page;

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
				page: this.page,
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

	// tag::websocket-handlers[]
	refreshAndGoToLastPage(message) {
		follow(client, root, [{
			rel: 'administratives',
			params: {size: this.state.pageSize}
		}]).done(response => {
			if (response.entity._links.last !== undefined) {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		})
	}
	
	refreshCurrentPage(message) {
		follow(client, root, [{
			rel: 'administratives',
			params: {
				size: this.state.pageSize,
				page: this.state.page.number
			}
		}]).then(administrativeCollection => {
			this.links = administrativeCollection.entity._links;
			this.page = administrativeCollection.entity.page;

			return administrativeCollection.entity._embedded.administratives.map(administrative => {
				return client({
					method: 'GET',
					path: administrative._links.self.href
				})
			});
		}).then(administrativePromises => {
			return when.all(administrativePromises);
		}).then(administratives => {
			this.setState({
				page: this.page,
				administratives: administratives,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}
	// end::websocket-handlers[]

	// tag::register-handlers[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
		stompClient.register([
			{ route: '/topic/newAdministrative', callback: this.refreshAndGoToLastPage },
			{ route: '/topic/updateAdministrative', callback: this.refreshCurrentPage },
			{ route: '/topic/deleteAdministrative', callback: this.refreshCurrentPage }
		]);
	}
	// end::register-handlers[]	

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate} />
				<AdministrativeList page={this.state.page}
					administratives={this.state.administratives}
					links={this.state.links}
					pageSize={this.state.pageSize}
					attributes={this.state.attributes}
					onNavigate={this.onNavigate}
					onUpdate={this.onUpdate}
					onDelete={this.onDelete}
					updatePageSize={this.updatePageSize}
					loggedInBoss={this.state.loggedInBoss} />
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

		const isBossCorrect = this.props.administrative.entity.boss && this.props.administrative.entity.boss.name == this.props.loggedInBoss;

		if (isBossCorrect === false) {
			return (
					<div>
						<a>Not Your Administrative</a>
					</div>
				)
		} else {
			return (
				<div>
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


	}

};

// tag::administrative-list[]
class AdministrativeList extends React.Component{

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

	// tag::handle-page-size-updates[]
	handleInput(e) {
		e.preventDefault();
		const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value =
				pageSize.substring(0, pageSize.length - 1);
		}
	}
	// end::handle-page-size-updates[]

	// tag::handle-nav[]
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}
	// end::handle-nav[]

	// tag::administrative-list-render[]
	render() {
		const pageInfo = this.props.page.hasOwnProperty("number") ?
			<h3>Administratives - Page {this.props.page.number + 1} of {this.props.page.totalPages}</h3> : null;

		const administratives = this.props.administratives.map(administrative =>
			<Administrative key={administrative.entity._links.self.href} 
				administrative={administrative}
				attributes={this.props.attributes}
				onUpdate={this.props.onUpdate}
				onDelete={this.props.onDelete}
				loggedInBoss={this.props.loggedInBoss} />
		);

		const navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
			<div>
				{pageInfo}
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Document Number</th>
							<th>Description</th>
							<th>Manager</th>
							<th></th>
							<th></th>
						</tr>
						{administratives}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>
			</div>
		)
	}
	// end::administrative-list-render[]
}
// end::administrative-list[]

// tag::administrative[]
class Administrative extends React.Component{

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.administrative);
	}

	render() {
		return (
			<tr>
				<td>{this.props.administrative.entity.firstName}</td>
				<td>{this.props.administrative.entity.lastName}</td>
				<td>{this.props.administrative.entity.documentNumber}</td>
				<td>{this.props.administrative.entity.description}</td>
				<td>{this.props.administrative.entity.boss ? this.props.administrative.entity.boss.name : null}</td>
				<td>
					<UpdateDialog administrative={this.props.administrative}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate}
								  loggedInBoss={this.props.loggedInBoss}/>
				</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}
// end::administrative[]

// tag::render[]
ReactDOM.render(
	<App loggedInBoss={document.getElementById('bossname').innerHTML }/>,
	document.getElementById('react')
)