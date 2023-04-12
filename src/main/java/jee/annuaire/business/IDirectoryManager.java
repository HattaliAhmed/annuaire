package jee.annuaire.business;

import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import jee.annuaire.web.User;

import java.util.Collection;

public interface IDirectoryManager {

    /**
     * Retrieves a Person object by their ID, and optionally removes sensitive information
     * based on the user's role and permissions.
     *
     * @param user The User object representing the current user.
     * @param personId The ID of the person to retrieve.
     * @return The retrieved Person object, or null if not found or not authorized.
     */
    Person findPersonById(User user, long personId);

    /**
     * Retrieves all Group objects from the directory.
     *
     * @return A Collection of all Group objects.
     */
    Collection<Groupe> findAllGroups();

    /**
     * Retrieves a Group object by its ID.
     *
     * @param groupId The ID of the group to retrieve.
     * @return The retrieved Groupe object, or null if not found.
     */
    Groupe findGroupById(long groupId);

    /**
     * Retrieves a Collection of Person objects by name, and optionally removes
     * sensitive information based on the user's role and permissions.
     *
     * @param user The User object representing the current user.
     * @param name The name to search for.
     * @return A Collection of Person objects that match the given name.
     */
    Collection<Person> findPersonByName(User user, String query);

    Collection<Groupe> findGroupByName(String query);

Collection<Person> findPersonByGroup(User user, long groupId, String query);
    /**
     * Authenticates a user by their ID and password.
     *
     * @param user The User object representing the current user.
     * @param personId The ID of the person to authenticate.
     * @param password The password to authenticate against.
     * @return true if the authentication is successful, false otherwise.
     */
    boolean login(User user, long personId, String password);

    /**
     * Logs out a user by resetting their ID, first name, and last name.
     *
     * @param user The User object representing the current user.
     */
    void logout(User user);

    /**
     * Saves a Person object to the directory, if the user is authorized.
     *
     * @param user The User object representing the current user.
     * @param p The Person object to save.
     * @return The saved Person object, or null if not authorized.
     */
    Person savePerson(User user, Person p);

    /**
     * Saves a Group object to the directory, if the user is authorized.
     *
     * @param user The User object representing the current user.
     * @param g The Group object to save.
     * @return The saved Group object, or null if not authorized.
     */
    Groupe saveGroup(User user, Groupe g);

}
