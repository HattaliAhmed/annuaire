package jee.annuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnnuaireApplicationTests {

	@Autowired
	DirectoryDao directoryDao;

	private final int GROUP_COUNT = 2;
	private final int PERSON_COUNT = 4;

	@BeforeEach
	public void init() {
		for (int i = 0; i < GROUP_COUNT; i++) {
			Groupe groupe = new Groupe();
			groupe.setName("group" + i);
			directoryDao.saveGroup(groupe);
		}

		for (int i = 0; i < PERSON_COUNT; i++) {
			Person person = new Person();
			person.setFirstName("person" + i);
			person.setGroupe(directoryDao.findGroupById((i % GROUP_COUNT)));
			directoryDao.savePerson(person);
		}
	}

	@Test
	public void initCheck() {
		// check number of groups
		assertTrue(directoryDao.findAllGroups().size() == GROUP_COUNT);

		// check number of persons
		assertTrue(directoryDao.findAllPersons().size() == PERSON_COUNT);
	}

	@Test
	public void findPersonByIdCheck() {
	}

}
