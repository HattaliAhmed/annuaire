package jee.annuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JUnit test class for the DirectoryDao implementation.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaoTest {

	@Autowired
	DirectoryDao directoryDao;

	private final int GROUP_COUNT = 2;
	private final int PERSON_COUNT = 4;

	/**
	 * Initializes the directory with test data.
	 */
	@BeforeAll
	public void init() {
		for (int i = 0; i < GROUP_COUNT; i++) {
			Groupe groupe = new Groupe();
			groupe.setName("group" + i);
			directoryDao.saveGroup(groupe);
			System.out.println("groupe " + i + " saved");
		}

		for (int i = 0; i < PERSON_COUNT; i++) {
			Person person = new Person();
			person.setFirstName("person" + i);
			person.setGroupe(directoryDao.findGroupById((i % GROUP_COUNT)));
			directoryDao.savePerson(person);
			System.out.println("person " + i + " saved");
		}
	}

	/**
	 * Tests the findAllGroups method of DirectoryDao.
	 */
	@Test
	public void FindAllGroups() {
		Collection<Groupe> groups = directoryDao.findAllGroups();
		assertNotNull(groups);
		assertEquals(GROUP_COUNT, groups.size());
	}

	/**
	 * Tests the findAllPersons method of DirectoryDao.
	 */
	@Test
	public void FindAllPersons() {
		Collection<Person> persons = directoryDao.findAllPersons();
		assertNotNull(persons);
		assertEquals(PERSON_COUNT, persons.size());
	}

	/**
	 * Tests the findPersonById method of DirectoryDao.
	 */
	@Test
	public void findPersonById() {
		for (Person person : directoryDao.findAllPersons()) {
			Person person2 = directoryDao.findPersonById(person.getId());
			assertNotNull(person2);
			assertEquals(person.getId(), person2.getId());
		}
	}

	/**
	 * Tests the findGroupById method of DirectoryDao.
	 */
	@Test
	public void findGroupById() {
		for (Groupe groupe : directoryDao.findAllGroups()) {
			Groupe groupe2 = directoryDao.findGroupById(groupe.getId());
			assertNotNull(groupe2);
			assertEquals(groupe.getId(), groupe2.getId());
		}
	}

	/**
	 * Tests the findPersonsByGroup method of DirectoryDao.
	 */
	@Test
	public void findPersonsByGroup() {
		for (Groupe groupe : directoryDao.findAllGroups()) {
			Collection<Person> persons = directoryDao.findPersonsByGroup(groupe);
			assertNotNull(persons);
			if(persons.size() > 0){
				for (Person person : persons) {
					assertEquals(groupe.getId(), person.getGroupe().getId());
				}
			}
		}
	}




}
