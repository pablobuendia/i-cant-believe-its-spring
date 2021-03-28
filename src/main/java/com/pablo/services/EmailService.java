package com.pablo.services;

import com.pablo.domain.User;

public interface EmailService {

  public void sendConfirmationEmail(User user);
}
