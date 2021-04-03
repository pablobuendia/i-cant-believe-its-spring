package com.pablo.providers;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import com.pablo.domain.User;
import com.pablo.exceptions.UserNotFoundException;
import com.pablo.services.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    User user = null;
    try {
      user = userService.doesUserExist(username);
    } catch (UserNotFoundException e) {
    }

    if (user == null || !user.getEmail().equalsIgnoreCase(username)) {
      throw new BadCredentialsException("Username not found.");
    }
    Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    return new UsernamePasswordAuthenticationToken(user, password, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
