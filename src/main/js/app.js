'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client'); 

class App extends React.Component { 

	constructor(props) {
		super(props);
		this.state = {administratives: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/administrative'}).done(response => {
			this.setState({administratives: response.entity._embedded.administratives});
		});
	}

	render() {
		return (
			<AdministrativeList employees={this.state.administratives}/>
		)
	}
}

class AdministrativeList extends React.Component{
	render() {
		const administratives = this.props.administratives.map(employee =>
			<Administrative key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Id</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
					</tr>
					{administratives}
				</tbody>
			</table>
		)
	}
}


class Administrative extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.administrative.id}</td>
				<td>{this.props.administrative.firstName}</td>
				<td>{this.props.administrative.lastName}</td>
				<td>{this.props.administrative.description}</td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)