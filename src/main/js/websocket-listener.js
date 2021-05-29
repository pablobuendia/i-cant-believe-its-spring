'use strict';

const SockJS = require('sockjs-client'); // <1>
const Stomp = require('@stomp/stompjs'); // <2>

function register(registrations) {
	const socket = SockJS('/payroll'); // <3>
	const stompClient = Stomp.Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		registrations.forEach(function (registration) { // <4>
			stompClient.subscribe(registration.route, registration.callback);
		});
	});
}

module.exports.register = register;