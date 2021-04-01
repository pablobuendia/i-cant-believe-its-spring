package com.pablo.services;

import com.pablo.controllers.UnmatchingUserCredentialsException;
import com.pablo.domain.User;


public interface UserService {

  User save(User user);

  User doesUserExist(String email) throws UserNotFoundException;

  User isValidUser(String email, String password) throws UnmatchingUserCredentialsException;

}
