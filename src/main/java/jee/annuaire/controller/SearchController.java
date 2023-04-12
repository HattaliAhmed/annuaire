package jee.annuaire.controller;

import java.util.Collection;
import jee.annuaire.business.IDirectoryManager;
import jee.annuaire.model.Groupe;
import jee.annuaire.web.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jee.annuaire.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

  @Autowired
  IDirectoryManager directoryManager;

  protected final Log logger = LogFactory.getLog(getClass());

  @PostMapping("") // Update the @PostMapping annotation
  public String search(@ModelAttribute("user") User user, @RequestParam("query") String query, Model model) {
    Collection<Person> persons = directoryManager.findPersonByName(user, query);
    model.addAttribute("persons", persons);
    Collection<Groupe> groups = directoryManager.findGroupByName(query);
    model.addAttribute("query", query);
    return "search-result";
  }


  @PostMapping("/{group_id}") // Update the URL pattern
  public String FindPersonInGroup(@ModelAttribute("user") User user,
                                  @PathVariable("group_id") Long group_id, // Update to use @RequestParam
                                  @RequestParam("query") String query, // Update to use @RequestParam
                                  Model model) {
    Collection<Person> persons = directoryManager.findPersonByGroup(user, group_id, query);
    model.addAttribute("persons", persons);
    return "search-result";
  }



}
