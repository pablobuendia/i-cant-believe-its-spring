package com.pablo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.pablo.domain.User;
import com.pablo.repositories.UserDAO;
import com.pablo.services.EmailService;
import com.pablo.services.UserNotFoundException;
import com.pablo.services.UserService;
import com.pablo.services.UserServiceImpl;

/**
 * Repeatable annotation that is used to register extensions for the annotated test class or test
 * method.
 * 
 * @author Pablo
 *
 */
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

  @Mock // Also there's @MockBean, provided by Spring Boot, that can be used to mock one or more
        // components which are part of ApplicationContext.
  private UserDAO userDAO;
  @Mock
  private EmailService emailService;
  private UserService userService;


  @BeforeEach
  public void setUp() throws Exception {
    // this.userDAO = Mockito.mock(UserDAO.class); // This can be used instead of the @Mock
    // annotation in userDAO;
    this.userService = new UserServiceImpl(this.userDAO, this.emailService);
  }

  @AfterEach
  public void tearDown() throws Exception {

  }

  @Test
  @DisplayName("Throws exception if user with given email does not exist")
  void Should_throwException_When_UserDoesNotExist() {
    String email = "foo@bar.com";
    Mockito.when(this.userDAO.findByEmail(email)).thenReturn(new ArrayList<User>());
    assertThatThrownBy(() -> this.userService.doesUserExist(email))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessage("User does not exist in the database.");
  }

  @Disabled
  @Test
  @DisplayName("Throws exception if user with given email & password is not found in the database")
  void Should_throwException_When_UnmatchingUserCredentialsFound() {
    fail("Not yet implemented");
  }

}
