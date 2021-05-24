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
		this.state = {administratives: []};
	}

	componentDidMount() { // <2>
		client({method: 'GET', path: '/api/administratives'}).done(response => {
			this.setState({administratives: response.entity._embedded.administratives});
		});
	}

	render() { // <3>
		return (
            <div>
                <AdministrativeList administratives={this.state.administratives}/>
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

// 'use strict';

// const React = require('react');
// const ReactDOM = require('react-dom');
// const client = require('./client'); 

// class App extends React.Component { 

// 	constructor(props) {
// 		super(props);
// 		this.state = {administratives: []};
// 	}

// 	componentDidMount() {
// 		client({method: 'GET', path: '/api/administrative'}).done(response => {
// 			this.setState({administratives: response.entity._embedded.administratives});
// 		});
// 	}

// 	render() {
// 		return (
// 			<AdministrativeList employees={this.state.administratives}/>
// 		)
// 	}
// }

// class AdministrativeList extends React.Component{
// 	render() {
// 		const administratives = this.props.administratives.map(employee =>
// 			<Administrative key={employee._links.self.href} employee={employee}/>
// 		);
// 		return (
// 			<table>
// 				<tbody>
// 					<tr>
// 						<th>Id</th>
// 						<th>First Name</th>
// 						<th>Last Name</th>
// 						<th>Description</th>
// 					</tr>
// 					{administratives}
// 				</tbody>
// 			</table>
// 		)
// 	}
// }


// class Administrative extends React.Component{
// 	render() {
// 		return (
// 			<tr>
// 				<td>{this.props.administrative.id}</td>
// 				<td>{this.props.administrative.firstName}</td>
// 				<td>{this.props.administrative.lastName}</td>
// 				<td>{this.props.administrative.description}</td>
// 			</tr>
// 		)
// 	}
// }

// ReactDOM.render(
// 	<App />,
// 	document.getElementById('react')
// )