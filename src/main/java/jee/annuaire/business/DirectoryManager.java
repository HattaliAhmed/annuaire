package jee.annuaire.business;

import jee.annuaire.dao.IDirectoryDao;
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

        @Override
        public Collection<Person> findPersonByName(User user, String name) {
                // remove sensitive information from every returned person
                Collection<Person> persons = directoryDao.findPersonByName(name);
                for (Person p : persons) {
                        p.setPassword(null);

                        // if the user is not logged in, remove email and birthdate
                        if (!checkUserIsLoggedIn(user)) {
                                p.setEmail(null);
                                p.setBirthDate(null);
                        }
                }
                return persons;
        }

        @Override
        public Collection<Groupe> findGroupByName(String query) {
                return directoryDao.findGroupByName(query);
        }

        public Collection<Person> findPersonByGroup(User user, long groupId, String query) {
                Collection<Person> persons = directoryDao.findPersonInGroupByName(groupId, query);
                logger.info("findPersonByGroup: " + persons.size());
                for (Person p : persons) {
                        p.setPassword(null);
                        // if the user is not logged in, remove email and birthdate
                        if (!checkUserIsLoggedIn(user)) {
                                p.setEmail(null);
                                p.setBirthDate(null);
                        }
                }
                return persons;
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
                if (checkUserIsLoggedIn(user) || user.getUserId() != p.getId()) {
                        return null;
                }

                directoryDao.savePerson(p);
                p.setPassword(null);
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

}
