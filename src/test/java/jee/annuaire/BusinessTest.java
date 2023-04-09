package jee.annuaire;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import jee.annuaire.business.IDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jee.annuaire.dao.IDirectoryDao;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import jee.annuaire.web.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@SpringBootTest

@ActiveProfiles("DirectoryManager-test")
@RunWith(SpringJUnit4ClassRunner.class)
public class BusinessTest {

    @Autowired
    IDirectoryManager manager;

    @Autowired
    IDirectoryDao dao = Mockito.mock(IDirectoryDao.class);


    private User anonymous, walid, ahmed;
    private Person person, person2, person3;
    private Groupe group, group2;
    static LocalDate date, date2;

    @BeforeClass
    public static void beforeAll() {
        date = LocalDate.of(1998, 07, 23);
        date2 = LocalDate.of(2000, 06, 22);
    }

    @AfterClass
    public static void afterAll() {

    }

    @Before
    public void setUp() {
        anonymous = new User();
        walid = new User();
        ahmed = new User();

        anonymous.setId(-1L);
        anonymous.setName("Anonymous");
        anonymous.setFirstname("Anonymous");

        walid.setId((long)100);
        walid.setName("MEKIBES");
        walid.setFirstname("Walid");

        ahmed.setId(200L);
        ahmed.setName("HATTALI");
        ahmed.setFirstname("Ahmed");

        group = new Groupe();
        group.setId(1L);
        group.setName("IDL");
        group2 = new Groupe();
        group2.setId(2L);
        group2.setName("SID");


        person = new Person();
        person.setId(100L);
        person.setFirstName("MEKIBES");
        person.setLastName("Walid");
        person.setEmail("mekibes.w@etu.univ-amu.fr");
        person.setWebsite("www.walidsite.com");
        person.setBirthDate(date);
        person.setPassword("passwordwalid");
        person.setGroupe(group);

        person2 = new Person();
        person2.setId(200L);
        person2.setFirstName("HATTALI");
        person2.setLastName("Ahmed");
        person2.setEmail("hattali.h@etu.univ-amu.fr");
        person2.setWebsite("www.ahmedsite.com");
        person2.setBirthDate(date);
        person2.setPassword("passwordahmed");
        person2.setGroupe(group);

        person3 = new Person();
        person3.setId(300L);
        person3.setFirstName("CHAIB");
        person3.setLastName("Rafik");
        person3.setEmail("chaib.r@etu.univ-amu.fr");
        person3.setWebsite("www.rafiksite.com");
        person3.setBirthDate(date);
        person3.setPassword("passwordrafik");
        person3.setGroupe(group);



    }

    @After
    public void tearDown() {
        // pour plus tard
    }

    @Test
    public void testGetPersonAsAnonymous() {

        // GIVEN
        Mockito.when(dao.findPersonById(person.getId())).thenReturn(person);

        // WHEN
        Person result = manager.findPerson(anonymous, person.getId());

        // THEN
        assertNull(result);
    }

    @Test
    public void testGetPersonAsHimself() {

        // GIVEN
        Mockito.when(dao.findPersonById(person.getId()))
                .thenReturn(person);

        // WHEN
        Person result = manager.findPerson(walid, person.getId());

        // THEN
        assertEquals(result.getBirthDate(), person.getBirthDate());
        assertEquals(result.getEmail(), person.getEmail());
        assertEquals(result.getPassword(), person.getPassword());
        assertEquals(result.getId(), person.getId());

    }

    @Test
    public void testGetPersonAsSomeoneElse() {

        // GIVEN
        Mockito.when(dao.findPersonById(person.getId()))
                .thenReturn(person);

        // WHEN
        Person result = manager.findPerson(ahmed, person.getId());

        // THEN
        assertEquals(result.getBirthDate(), person.getBirthDate());
        assertEquals(result.getEmail(), person.getEmail());
        assertNotEquals(result.getPassword(), null);
        assertEquals(result.getId(), person.getId());

    }

    @Test
    public void testGetGroup() {

        // GIVEN
        Mockito.when(dao.findGroupById(group.getId()))
                .thenReturn(group);

        // WHEN
        Groupe result = manager.findGroup(walid, group.getId());

        // THEN
        assertEquals(result.getName(), group.getName());

    }

    // retourne true si un groupe appartient à une collection de groupes, sinon false
    private boolean groupIsInside (Groupe groupToFind, Collection<Groupe> list) {
        long id = groupToFind.getId();

        for (Groupe group : list) {
            if (group.getId() == id)
                return true;
        }
        return false;
    }

    @Test
    public void testGetAllGroups() {
        Collection<Groupe> groups = new ArrayList<Groupe>();
        groups.add(group);
        groups.add(group2);

        // GIVEN
        Mockito.when(dao.findAllGroups())
                .thenReturn(groups);

        // WHEN
        Collection<Groupe> result = manager.findAllGroups(walid);

        // THEN
        assertTrue(result.size() == 2);
        assertTrue(groupIsInside (group, result));
        assertTrue(groupIsInside (group2, result));

    }


    @Test
    public void testLoginSuccess() {

        // GIVEN
        Mockito.when(dao.findPersonById(person.getId()))
                .thenReturn(person);

        // WHEN
        boolean connected = manager.login(anonymous, person.getId(), person.getPassword());

        // THEN
        assertEquals(anonymous.getId(), person.getId());
        assertEquals(anonymous.getName(), person.getLastName());
        assertEquals(anonymous.getFirstname(), person.getFirstName());
        assertTrue(connected);
    }

    @Test
    public void testLoginFail() {

        // GIVEN
        Mockito.when(dao.findPersonById(person.getId()))
                .thenReturn(person);

        // WHEN
        boolean connected = manager.login(anonymous, person.getId(), "");

        // THEN
        assertEquals((long)anonymous.getId(), (long)-1L);
        assertEquals(anonymous.getName(), null);
        assertEquals(anonymous.getFirstname(), null);
        assertFalse(connected);

    }

    @Test
    public void testLogout() {

        // WHEN
        manager.logout(walid);

        // THEN
        assertEquals(walid.getId(), anonymous.getId());
        assertEquals(walid.getName(), anonymous.getName());
        assertEquals(walid.getFirstname(), anonymous.getFirstname());

    }

    @Test
    public void testSavePersonSuccess() {

        // WHEN
        manager.savePerson(ahmed, person2);

        // THEN
        assertEquals(ahmed.getId(), person2.getId());

    }

}