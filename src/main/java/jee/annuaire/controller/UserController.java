package jee.annuaire.controller;

import jee.annuaire.business.IDirectoryManager;
import jee.annuaire.web.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller()
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

  @Autowired
  IDirectoryManager directoryManager;

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
  public String showLoginPage(Model model) {
    // Add any necessary model attributes or data to be used in the login page
    return "login"; // Return the view name for the login page
  }

  // Handler method for processing the login form submission
  @PostMapping("/login")
  public String processLogin(@ModelAttribute("user") User user, @RequestParam("id") long id, @RequestParam("password") String password, Model model) {
    // Call the directoryManager's login method to check if the provided credentials are valid
    if (directoryManager.login(user, id, password)) {
      logger.info("login successful for " + user);
      // If the login is successful, redirect to a success page or perform any other necessary actions
      return "redirect:/groups"; // Update the return statement to redirect to "/groups"
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

}
