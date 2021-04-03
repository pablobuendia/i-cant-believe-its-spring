package com.pablo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.pablo.domain.User;
import com.pablo.exceptions.UserNotFoundException;
import com.pablo.repositories.UserDAO;
import com.pablo.services.EmailService;
import com.pablo.services.UserService;
import com.pablo.services.UserServiceImpl;

/**
 * Unit tests for Services. Repeatable annotation that is used to register extensions for the
 * annotated test class or test method.
 * 
 * @author Pablo
 *
 */
@ExtendWith(SpringExtension.class)
@Tag("Service")
public class UserServiceTests {

  @MockBean // @MockBean, provided by Spring Boot, differs from @Mock in that can be used to mock
            // one or more components which are part of ApplicationContext.
  private UserDAO userDAO;
  @Mock
  private EmailService emailService;
  private UserService userService;


  /**
   * 
   * @param testInfo The TestInfo instance is used to retrieve the information about the curent
   *        test, such as display name, test class, test method, and so on.
   * @throws Exception
   */
  @BeforeEach
  public void setUp(TestInfo testInfo) throws Exception {
    // this.userDAO = Mockito.mock(UserDAO.class); // This can be used instead of the @Mock
    // annotation in userDAO;
    this.userService = new UserServiceImpl(this.userDAO, this.emailService);
    String displayName = testInfo.getDisplayName();
    assertTrue(
        "Should return error message for when user not existing in the database tries to login."
            .equals(displayName));

  }

  @AfterEach
  public void tearDown() throws Exception {
    // Nothing to see here
  }

  @Test
  @RepeatedTest(5) // Used to repeat the tests for n number of times when n is the number passed to
                   // the annotation.
  @DisplayName("Throws exception if user with given email does not exist")
  void Should_throwException_When_UserDoesNotExist() {
    String email = "foo@bar.com";
    Mockito.when(this.userDAO.findByEmail(email)).thenReturn(new ArrayList<User>());

    // Usage of Java 8 Lambda expression in assertion method
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
