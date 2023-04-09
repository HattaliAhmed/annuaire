package jee.annuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import jee.annuaire.dao.DirectoryDao;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * JUnit test class for the DirectoryDao implementation.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaoTest {

	@Autowired
	DirectoryDao directoryDao;


	@Test
	@Transactional
	void testPopulate() {
		Collection<Groupe> groupes = directoryDao.findAllGroups();
		// check that the database is populated and there is at least one group
		assertNotNull(groupes);
		assertEquals(true, groupes.size() > 0);

		// Check that there is at least one person in the database
		assertEquals(true, directoryDao.findPersonByName("").size() > 0);

		// get a random person
		Person person = directoryDao.findPersonById(1);
		assertNotNull(person);
		// Check that the person has a group
		assertNotNull(person.getGroupe());

		// check that each group has at least one person
		for (Groupe groupe : groupes) {
			Collection<Person> personnes = groupe.getMembers();
			assertNotNull(personnes);
			assertEquals(true, personnes.size() > 0);
		}
	}


	@Test
	@Transactional
	void testFindPersonById() {
		Person person = directoryDao.findPersonById(1);
		assertNotNull(person);
		assertEquals(1, person.getId());
	}

	@Test
	@Transactional
	void testFindPersonByName() {
		Person p = directoryDao.findPersonById(1);
		p.setFirstName("Ahmed");
		p.setLastName("Walid");
		directoryDao.savePerson(p);
		directoryDao.findPersonByName("Walid");
		assertEquals(true, directoryDao.findPersonByName("Walid").size() > 0);
	}

}
