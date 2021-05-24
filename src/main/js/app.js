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