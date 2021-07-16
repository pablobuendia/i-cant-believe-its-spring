package com.pablo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.pablo.helpers.HelloMessage;

public class MessageTest {

	@Test
	public void messageEmpty() throws Exception {
		HelloMessage message = new HelloMessage();
		assertTrue(message.getName().isEmpty());
	}
}
