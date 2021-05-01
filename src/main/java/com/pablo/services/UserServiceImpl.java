package com.pablo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pablo.domain.User;
import com.pablo.exceptions.UnmatchingUserCredentialsException;
import com.pablo.exceptions.UserNotFoundException;
import com.pablo.repositories.UserDAO;

/**
 * Implementation of the UserService
 * 
 * @author Pablo
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;
	private EmailService emailService;

	@Autowired
	public UserServiceImpl(UserDAO userDAO, EmailService emailService) {
		this.userDAO = userDAO;
		this.emailService = emailService;
	}

	@Override
	public User save(User user) {
		User userSaved = userDAO.save(user);
		this.emailService.sendConfirmationEmail(userSaved);

		return userSaved;
	}

	@Override
	public void update(User user) {
		userDAO.update(user);
	}

	public User doesUserExist(String email) throws UserNotFoundException {
		List<User> users = (List<User>) userDAO.findByEmail(email);
		if (users.size() == 0) {
			throw new UserNotFoundException("User does not exist in the database.");
		}
		return users.get(0);
	}

	@Override
	public User isValidUser(String email, String password)
			throws UnmatchingUserCredentialsException {

		List<User> users = (List<User>) userDAO.findByEmailAndPassword(email, password);
		if (users == null || users.size() == 0) {
			throw new UnmatchingUserCredentialsException(
					"User with given credentials is not found in the database.");
		}
		return users.get(0);
	}
}
