package jee.annuaire.controller;

import java.util.Collection;
import jee.annuaire.business.IDirectoryManager;
import jee.annuaire.dao.IDirectoryDao;
import jee.annuaire.model.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups")
public class GroupeController{

  @Autowired
  IDirectoryManager directoryManager;

  @GetMapping("")
  public String showGroups(Model model) {
    Collection<Groupe> groups = directoryManager.findAllGroups();
    model.addAttribute("groups", groups);
    return "groups";
  }

  @GetMapping("/{groupId}")
  public String showGroupMembers(@PathVariable Long groupId, Model model) {
    Groupe group = directoryManager.findGroupById(groupId);
    model.addAttribute("group", group);
    return "group-members";
  }
}
