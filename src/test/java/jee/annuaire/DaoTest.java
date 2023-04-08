package jee.annuaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import jee.annuaire.dao.DirectoryDao;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import org.junit.jupiter.api.BeforeAll;
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
	}


}
