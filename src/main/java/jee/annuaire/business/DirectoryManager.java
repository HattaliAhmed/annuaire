package jee.annuaire.business;

import jee.annuaire.dao.IDirectoryDao;
import jee.annuaire.model.Person;
import jee.annuaire.model.Groupe;
import jee.annuaire.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("directoryManager")
public class DirectoryManager implements IDirectoryManager{

        @Autowired
        private IDirectoryDao directoryDao;

        public DirectoryManager(IDirectoryDao directoryDao) {
                this.directoryDao = directoryDao;
        }

        @Override
        public void newUser(User user) {
            // TODO : Implémenter la méthode
        }

        //Long idAnonymous = -1L;

        public class UserNotLoggedInException extends RuntimeException {
                public UserNotLoggedInException(String message) {
                        super(message);
                }
        }


        private boolean checkUserIsLoggedIn(User user) {
                if (user == null || -1L == (user.getId())) {
                        //throw new UserNotLoggedInException("User not logged in");
                        return false;
                }
                return true;
        }

        @Override
        public Person findPerson(User user, long personId) {
                if (!checkUserIsLoggedIn(user)) {
                        //throw new UserNotLoggedInException("Person not found !");
                        return null;
                }
                return directoryDao.findPersonById(personId);
        }


        @Override
        public Groupe findGroup(User user, long groupId) {
                if (!checkUserIsLoggedIn(user)) {
                        throw new UserNotLoggedInException("Person not found");
                }
                return directoryDao.findGroupById(groupId);
        }

        @Override
        public Collection<Groupe> findAllGroups(User user) {
                if (!checkUserIsLoggedIn(user)) {
                        throw new UserNotLoggedInException("Group not found !");
                }
                return directoryDao.findAllGroups();
        }

        @Override
        public boolean login(User user, long personId, String password) {
                Person person = directoryDao.findPersonById(personId);
                if (person == null || !person.getPassword().equals(password)) {
                        return false;
                }
                user.setId(person.getId());
                user.setName(person.getLastName());
                user.setFirstname(person.getFirstName());

                return true;
        }

        @Override
        public void logout(User user) {
                user.setId(-1L);
                user.setName("Anonymous");
                user.setFirstname("Anonymous");
        }

        @Override
        public void savePerson(User user, Person p) {
                if (!checkUserIsLoggedIn(user)) {
                        throw new UserNotLoggedInException("Person not found !");
                }
                directoryDao.savePerson(p);
        }


}
