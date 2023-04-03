package mybootapp.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import mybootapp.model.Groupe;
import mybootapp.model.Person;
import mybootapp.repo.DirectoryDao;
import mybootapp.web.Starter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
public class DaoTest {

    @Autowired
    DirectoryDao directoryDao;
    
	@BeforeEach
	public void init() {

		int GROUP_COUNT = 2;
		int PERSON_COUNT = 4;

		for (int i = 0; i < GROUP_COUNT; i++) {
			Groupe groupe = new Groupe();
			groupe.setId((long)i);
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
	public void testFindAllGroups() {
		assertTrue(true);
	}
}
