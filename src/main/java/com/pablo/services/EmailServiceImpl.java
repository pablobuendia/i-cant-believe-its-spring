package com.pablo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pablo.domain.User;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class EmailServiceImpl implements EmailService {

  @Override
  public void sendConfirmationEmail(User user) {
    try {
      // Code for sending email
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}
