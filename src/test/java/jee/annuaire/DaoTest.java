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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DaoTest {

	@Autowired
	DirectoryDao directoryDao;

	private final int GROUP_COUNT = 2;
	private final int PERSON_COUNT = 4;

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

	@Test
	public void FindAllGroups() {
		Collection<Groupe> groups = directoryDao.findAllGroups();
		assertNotNull(groups);
		assertEquals(GROUP_COUNT, groups.size());
	}
	@Test
	public void FindAllPersons() {
		Collection<Person> persons = directoryDao.findAllPersons();
		assertNotNull(persons);
		assertEquals(PERSON_COUNT, persons.size());
	}

	@Test
	public void findPersonById() {
		for (Person person : directoryDao.findAllPersons()) {
			Person person2 = directoryDao.findPersonById(person.getId());
			assertNotNull(person2);
			assertEquals(person.getId(), person2.getId());
		}
	}


}
