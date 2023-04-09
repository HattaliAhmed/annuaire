package jee.annuaire.business;

import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import jee.annuaire.web.User;

import java.util.Collection;

public interface IDirectoryManager {
    void newUser(User user);

    Person findPerson(User user, long personId);

    Groupe findGroup(User user, long groupId);

    Collection<Groupe> findAllGroups(User user);

    boolean login(User user, long personId, String password);

    void logout(User user);

    void savePerson(User user, Person p);
}
