package com.pablo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pablo.domain.User;
import com.pablo.repositories.UserDAO;

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
}
