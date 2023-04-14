package jee.annuaire.controller;

import java.time.LocalDateTime;
import java.util.UUID;
import jee.annuaire.business.IDirectoryManager;
import jee.annuaire.model.PasswordResetToken;
import jee.annuaire.model.Person;
import jee.annuaire.web.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.mail.javamail.JavaMailSender;


@Controller()
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

  @Autowired
  IDirectoryManager directoryManager;

  @Autowired
  private JavaMailSender mailSender;

  protected final Log logger = LogFactory.getLog(getClass());

  @ModelAttribute("user")
  public User newUser() {
    var user = new User();
    user.setUserId(-1L);
    logger.info("new user " + user);
    return user;
  }

  // Handler method for displaying the login page
  @GetMapping("/login")
  public String showLoginPage(@ModelAttribute("user") User user) {
    // Add any necessary model attributes or data to be used in the login page
    if(user.getUserId() != -1L) {
      return "redirect:/";
    }
    return "login"; // Return the view name for the login page
  }

  // Handler method for processing the login form submission
  @PostMapping("/login")
  public String processLogin(@ModelAttribute("user") User user, @RequestParam("id") String idString, @RequestParam("password") String password, Model model) {
    // Validate if id is a number
    long id;
    try {
      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {
      model.addAttribute("error", "User ID must be a number");
      return "login";
    }

    // Call the directoryManager's login method to check if the provided credentials are valid
    if (directoryManager.login(user, id, password)) {
      logger.info("login successful for " + user);
      // If the login is successful, redirect to a success page or perform any other necessary actions
      return "redirect:/"; // Update the return statement to redirect to "/groups"
    } else {
      logger.info("login failed for " + user);
      // If the login fails, add an error message to the model and return to the login page
      model.addAttribute("error", "Invalid username or password");
      return "login"; // Return the view name for the login page
    }
  }


  // Handle logout requests
  @GetMapping("/logout")
  public String logout(@ModelAttribute("user") User user) {
    // Call the directoryManager's logout method to invalidate the user's session
    directoryManager.logout(user);
    logger.info("logout successful for " + user);
    // Redirect to the login page
    return "redirect:/user/login"; // Update the return statement to redirect to "/user/login"
  }

  @GetMapping("/forgotPassword")
  public String forgotPassword() {
    // Render the password recovery form
    return "forgot-password"; // Return the view name for the password recovery form
  }

  @PostMapping("/forgotPassword")
  public String submitForgotPasswordForm(String email, RedirectAttributes redirectAttributes) {
    // TODO: Implement password reset logic here
    // You can use the email address to send a password reset link or perform other actions
    Long personId = directoryManager.findPersonByEMail(email);

    if (personId < 0) {
      // Add error message to redirect attributes
      redirectAttributes.addFlashAttribute("error", "Email address not found: " + email);
      return "redirect:/user/forgotPassword";
    }

    // Generate a password reset token
    PasswordResetToken token = new PasswordResetToken();
    token.setPersonId(personId);
    token.setToken(new UUID(0, 0).randomUUID().toString());
    token.setExpiryDate(LocalDateTime.now().plusHours(24));

    // we save the token in the database
    directoryManager.saveToken(token);

    // we send the email
    // Prepare the email message
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("hattaliahmed@gmail.com");
    message.setSubject("Password Reset");
    message.setText("Click the following link to reset your password: http://localhost:8080/user/reset?token=" + token.getToken());
    mailSender.send(message);

    // Add success message to redirect attributes
    redirectAttributes.addFlashAttribute("success", "Password reset instructions sent to " + email);

    return "redirect:/user/forgotPassword";
  }

  @GetMapping("/reset")
  public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
    // Find the token in the database
    boolean valid = directoryManager.verifyToken(token);

    if (!valid) {
      // Token not found or expired, show error message
      model.addAttribute("error", "Invalid or expired password reset link.");
      return "redirect:/user/forgotPassword";
    }

    // Token is valid, show the reset password form
    model.addAttribute("token", token);
    return "reset-password";
  }

  @PostMapping("/reset")
  public String submitResetPasswordForm(@ModelAttribute("user") User user,
                                        @RequestParam("token") String token,
                                        @RequestParam("password") String password,
                                        @RequestParam("confirmPassword") String confirmPassword,
                                        Model model) {
    // Find the token in the database
    boolean valid = directoryManager.verifyToken(token);

    if (!valid) {
      logger.info("invalid token");
      // Token not found or expired, show error message
      model.addAttribute("error", "Invalid or expired password reset link.");
      return "redirect:/user/forgotPassword";
    }
    PasswordResetToken t = directoryManager.findToken(token);
    user.setUserId(t.getPersonId());
    user.setFirstName(directoryManager.findPersonById(user, t.getPersonId()).getFirstName());
    user.setLastName(directoryManager.findPersonById(user, t.getPersonId()).getLastName());

    if (!password.equals(confirmPassword)) {
      // Password and confirm password do not match, show error message
      model.addAttribute("error", "Passwords do not match.");
      return "reset-password";
    }

    if(password.length() < 8) {
      // Password is too short, show error message
      model.addAttribute("error", "Password must be at least 8 characters long.");
      return "reset-password";
    }

    Person p = directoryManager.findPersonById(user, directoryManager.findToken(token).getPersonId());
    p.setPassword(password);
    directoryManager.savePerson(user, p);

    // Show success message
    model.addAttribute("success", "Password reset successful.");
    return "redirect:/user/login";
  }
}
