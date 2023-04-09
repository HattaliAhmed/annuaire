package jee.annuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
		assertEquals(true, directoryDao.findPersonByName("Ahmed").size() > 0);
		assertEquals(true, directoryDao.findPersonByName("Ahmed Walid").size() > 0);
		assertEquals(true, directoryDao.findPersonByName("Walid Ahmed").size() > 0);
	}

	@Test
	@Transactional
	void testFindGroupById() {
		Groupe groupe = directoryDao.findGroupById(1);
		assertNotNull(groupe);
		assertEquals(1, groupe.getId());
	}

	@Test
	@Transactional
	void testFindGroupByName() {
		Groupe g = directoryDao.findGroupById(1);
		g.setName("Groupe 1");
		directoryDao.saveGroup(g);
		directoryDao.findGroupByName("Groupe 1");
		assertEquals(true, directoryDao.findGroupByName("Groupe 1").size() > 0);
	}

	@Test
	@Transactional
	void testSaveGroupe() {
		Groupe g = new Groupe();
		g.setName("Groupe Test");
		directoryDao.saveGroup(g);
		assertEquals(true, directoryDao.findGroupByName("Groupe Test").size() > 0);
	}

	@Test
	@Transactional
	void testSavePerson() {
		Person p = new Person();
		p.setLastName("Test");
		p.setFirstName("Test");
		assertEquals(true, directoryDao.findPersonByName("Test").size() == 0);
		directoryDao.savePerson(p);
		assertEquals(true, directoryDao.findPersonByName("Test").size() > 0);
	}

	@Test
	@Transactional
	void testDeleteGroupe() {
		Groupe g = directoryDao.findGroupById(1);
		assertNotNull(g);
		directoryDao.deleteGroup(g);
		assertNull(directoryDao.findGroupById(1));
	}

	@Test
	@Transactional
	void testDeletePerson() {
		Person p = directoryDao.findPersonById(1);
		assertNotNull(p);
		directoryDao.deletePerson(p);
		assertNull(directoryDao.findPersonById(1));
	}

}
