package jee.annuaire.controller;

import jee.annuaire.business.IDirectoryManager;
import jee.annuaire.model.Groupe;
import jee.annuaire.web.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jee.annuaire.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/person")
@SessionAttributes("user")
public class PersonController {

  @ModelAttribute("user")
  public User newUser() {
    var user = new User();
    user.setUserId(-1L);
    logger.info("new user " + user);
    return user;
  }

  @Autowired
  IDirectoryManager directoryManager;

  protected final Log logger = LogFactory.getLog(getClass());

  @GetMapping("/{id}")
  public String showPerson(@ModelAttribute("user") User user, @PathVariable("id") Long idP, Model model) {
    Person person = directoryManager.findPersonById(user, idP);
    logger.info("Person: " + person.getBirthDate());
    logger.info("User: " + user.getUserId());
    model.addAttribute("person", person);
    return "person-details";
  }

  @GetMapping("/edit/{id}")
  public String showEditForm(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model model) {
    Person person = directoryManager.findPersonById(user, id);
    model.addAttribute("person", person);
    model.addAttribute("groups", directoryManager.findAllGroups());
    return "edit-person";
  }

  @PostMapping("/edit/{id}")
  public String processEditForm(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                                @RequestParam("groupId") Long groupId, @RequestParam("newPassword") String newPassword, @Validated @ModelAttribute("person") Person person,
                                BindingResult result, Model model) {

    // Load the existing person and group from the database
    Person p = directoryManager.findPersonById(user, id);

    if (result.hasErrors()) {
      // If validation errors occur, return to the edit form with error messages
      model.addAttribute("groups", directoryManager.findAllGroups()); // Add groups to the model

      // log all errors
      result.getAllErrors().forEach(error -> logger.error(error));
      return "edit-person";
    }else if (!p.getPassword().equals(person.getPassword())) {
      // If validation errors occur, return to the edit form with error messages
      model.addAttribute("groups", directoryManager.findAllGroups()); // Add groups to the model
      // add error to result
      result.rejectValue("password", "error.password", "Password is incorrect");
      return "edit-person";
    }

    // Changing password if newPassword is not empty and is at least 8 characters.
    if(!newPassword.isEmpty()){
      if(newPassword.length() < 8){
        // If validation errors occur, return to the edit form with error messages
        model.addAttribute("groups", directoryManager.findAllGroups()); // Add groups to the model
        // add error to result
        result.rejectValue("newPassword", "error.newPassword", "Password must be at least 8 characters");
        return "edit-person";
      }
      p.setPassword(newPassword);
    }

    Groupe g = directoryManager.findGroupById(groupId);

    // Update the person's properties with the provided form data
    p.setFirstName(person.getFirstName());
    p.setLastName(person.getLastName());
    p.setEmail(person.getEmail());
    p.setBirthDate(person.getBirthDate());
    p.setGroupe(g);

    // Update the group's list of persons with the edited person
    p.getGroupe().getMembers().remove(p);
    p.setGroupe(g);
    g.getMembers().add(p);

    // Save the changes to the database
    directoryManager.savePerson(user, p);
    directoryManager.saveGroup(user, g);

    return "redirect:/person/" + id;
  }

}
