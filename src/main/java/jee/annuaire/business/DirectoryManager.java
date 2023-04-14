package jee.annuaire.business;

import java.util.ArrayList;
import jee.annuaire.dao.IDirectoryDao;
import jee.annuaire.model.PasswordResetToken;
import jee.annuaire.model.Person;
import jee.annuaire.model.Groupe;
import jee.annuaire.web.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("directoryManager")
public class DirectoryManager implements IDirectoryManager{

        @Autowired
        private IDirectoryDao directoryDao;

        // rendre un utilisateur anonyme
        void newUser(User user) {
                user.setUserId(-1L);
                user.setFirstName("Anonymous");
                user.setLastName("");
        }

        /**
         * Vérifie si l'utilisateur est connecté ou anonyme.
         * @param user
         * @return true si l'utilisateur est connecté, false sinon.
         */
        private boolean checkUserIsLoggedIn(User user) {
                if(user == null || user.getUserId() == null || user.getUserId() == -1L){
                        return false;
                }
                return true;
        }
        protected final Log logger = LogFactory.getLog(getClass());

        @Override
        public Person findPersonById(User user, long personId) {
                Person p = directoryDao.findPersonById(personId);
                // If the person is not found, return null
                if(p == null) {
                        return null;
                }

                if(checkUserIsLoggedIn(user)){
                        if(user.getUserId() != p.getId()){
                                p.setPassword(null);
                        }
                }
                else {
                        p.setPassword(null);
                        p.setEmail(null);
                        p.setBirthDate(null);
                }
                return p;
        }


        @Override
        public Groupe findGroupById(long groupId) {
                return directoryDao.findGroupById(groupId);
        }

        @Override
        public Collection<Groupe> findAllGroups() {
                return directoryDao.findAllGroups();
        }

        public Collection<Person> findPersonByName(User user, String name) {
                // Retrieve the list of persons from the database
                Collection<Person> persons = directoryDao.findPersonByName(name);

                // Create a new list to store the modified public persons
                Collection<Person> publicPersons = new ArrayList<Person>();

                // Loop through each person in the original list
                for (Person p : persons) {
                        // Create a new PublicPerson object with only the non-sensitive information
                        Person publicPerson = new Person();
                        publicPerson.setId(p.getId());
                        publicPerson.setFirstName(p.getFirstName());
                        publicPerson.setLastName(p.getLastName());
                        publicPerson.setWebsite(p.getWebsite());
                        publicPerson.setGroupe(p.getGroupe());
                        // Add the modified PublicPerson object to the new list
                        publicPersons.add(publicPerson);
                }

                // If the user is not logged in, remove email and birthdate from the modified public persons
                if (!checkUserIsLoggedIn(user)) {
                        for (Person p : publicPersons) {
                                p.setEmail(null);
                                p.setBirthDate(null);
                                p.setPassword(null);
                        }
                }

                return publicPersons;
        }


        @Override
        public Collection<Groupe> findGroupByName(String query) {
                return directoryDao.findGroupByName(query);
        }

        public Collection<Person> findPersonByGroup(User user, long groupId, String query) {
                Collection<Person> persons = directoryDao.findPersonInGroupByName(groupId, query);
                logger.info("findPersonByGroup: " + persons.size());
                Collection<Person> publicPersons = new ArrayList<>();
                for (Person p : persons) {
                        // Create a new PublicPerson object with only the non-sensitive information
                        Person publicPerson = new Person();
                        publicPerson.setId(p.getId());
                        publicPerson.setFirstName(p.getFirstName());
                        publicPerson.setLastName(p.getLastName());
                        publicPerson.setWebsite(p.getWebsite());
                        publicPerson.setGroupe(p.getGroupe());
                        // If the user is not logged in, remove email and birthdate from the PublicPerson object
                        if (!checkUserIsLoggedIn(user)) {
                                publicPerson.setEmail(null);
                                publicPerson.setBirthDate(null);
                        }
                        // Add the PublicPerson object to the collection
                        publicPersons.add(publicPerson);
                }
                return publicPersons;
        }

        @Override
        public Long findPersonByEMail(String email) {
                return directoryDao.findPersonByEmail(email);
        }


        @Override
        public boolean login(User user, long personId, String password) {
                Person person = directoryDao.findPersonById(personId);
                if (person == null || !person.getPassword().equals(password)) {
                        return false;
                }
                user.setUserId(person.getId());
                user.setLastName(person.getLastName());
                user.setFirstName(person.getFirstName());

                return true;
        }

        @Override
        public void logout(User user) {
                user.setUserId((long)-1);
                user.setFirstName("Anonymous");
                user.setLastName("Anonymous");
        }

        @Override
        public Person savePerson(User user, Person p) {
                // If the user is not logged in or user's id doesn't match person's id, return null.
                if (!checkUserIsLoggedIn(user) || user.getUserId() != p.getId()) {
                        return null;
                }
                directoryDao.savePerson(p);
                return p;
        }

        @Override
        public Groupe saveGroup(User user, Groupe g) {
                // If the user is not logged in or user's id isn't in the group, return null.
                if (!checkUserIsLoggedIn(user) || !g.getMembers().contains(user.getUserId())) {
                        return null;
                }
                directoryDao.saveGroup(g);
                return g;
        }

        @Override
        public PasswordResetToken saveToken(PasswordResetToken token) {
                return directoryDao.saveToken(token);
        }

        @Override
        public boolean verifyToken(String token) {
                return directoryDao.verifyToken(token);
        }

        @Override
        public PasswordResetToken findToken(String token) {
                return directoryDao.findToken(token);
        }
}
