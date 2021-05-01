package com.pablo.services;

import com.pablo.domain.User;
import com.pablo.exceptions.UnmatchingUserCredentialsException;
import com.pablo.exceptions.UserNotFoundException;

public interface UserService {

	User save(User user);

	User doesUserExist(String email) throws UserNotFoundException;

	User isValidUser(String email, String password)
			throws UnmatchingUserCredentialsException;

	void update(User user);

}
