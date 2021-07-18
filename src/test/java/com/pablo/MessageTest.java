package com.pablo;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.util.ObjectUtils;

import com.pablo.helpers.HelloMessage;

public class MessageTest {

	private MessageTest(TestInfo info) {
		System.out.println("Working on test: " + info.getDisplayName());
	}

	@DisplayName("<= Message is empty =>")
	@Test
	void messageEmpty() throws Exception {
		HelloMessage message = new HelloMessage();
		assertNull(ObjectUtils.isEmpty(message.getName()),
				"hellomessage should have no name");
		assertNull(ObjectUtils.isEmpty(message.getName()),
				() -> "HelloMessage should have no name");
	}
}
