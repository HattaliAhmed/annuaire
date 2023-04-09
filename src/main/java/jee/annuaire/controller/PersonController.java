package jee.annuaire.controller;

import jee.annuaire.model.Groupe;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jee.annuaire.dao.IDirectoryDao;
import jee.annuaire.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/person")
public class PersonController {

  @Autowired
  IDirectoryDao directoryDao;

  protected final Log logger = LogFactory.getLog(getClass());

  @GetMapping("/{id}")
  public String showPerson(@PathVariable("id") Long id, Model model) {
    Person person = directoryDao.findPersonById(id);
    model.addAttribute("person", person);
    return "person-details";
  }

  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable("id") Long id, Model model) {
    Person person = directoryDao.findPersonById(id);
    model.addAttribute("person", person);
    model.addAttribute("groups", directoryDao.findAllGroups());
    return "edit-person";
  }

  @PostMapping("/edit/{id}")
  public String processEditForm(@PathVariable("id") Long id, @RequestParam("groupId") Long groupId, @ModelAttribute("person") Person person) {
    // Load the existing person and group from the database
    Person p = directoryDao.findPersonById(id);
    Groupe g = directoryDao.findGroupById(groupId);

    // Update the person's properties with the provided form data
    p.setFirstName(person.getFirstName());
    p.setLastName(person.getLastName());
    p.setEmail(person.getEmail());

    // Update the group's list of persons with the edited person
    p.getGroupe().getMembers().remove(p);
    p.setGroupe(g);
    g.getMembers().add(p);

    // Save the changes to the database
    directoryDao.savePerson(p);
    directoryDao.saveGroup(g);

    return "redirect:/person/" + id;
  }

}
