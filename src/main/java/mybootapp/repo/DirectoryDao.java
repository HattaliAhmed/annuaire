package mybootapp.repo;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import mybootapp.model.Group;
import mybootapp.model.Person;

@Repository
@Transactional

public class DirectoryDao implements IDirectoryDao {

	    @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    public Collection<Group> findAllGroups() {
	        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g", Group.class);
	        return query.getResultList();
	    }

	    @Override
	    public Collection<Person> findAllPersons() {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
	        return query.getResultList();
	    }
	    
	    @Override
	    public Collection<Person> findPersonsByGroup(Group group) {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p JOIN p.groups g WHERE g = :group", Person.class);
	        query.setParameter("group", group);
	        return query.getResultList();
	    }


	    @Override
	    public Person findPersonById(long id) {
	        return entityManager.find(Person.class, id);
	    }

	    @Override
	    public Group findGroupById(long id) {
	        return entityManager.find(Group.class, id);
	    }

	    @Override
	    public Group findGroupByName(String name) {
	        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g WHERE g.name = :name", Group.class);
	        query.setParameter("name", name);
	        return query.getSingleResult();
	    }

	    @Override
	    public void savePerson(Person p) {
	        entityManager.persist(p);
	    }

	    @Override
	    public void saveGroup(Group g) {
	        entityManager.persist(g);
	    }

	    @Override
	    public void deletePerson(Person p) {
	        entityManager.remove(entityManager.contains(p) ? p : entityManager.merge(p));
	    }

	    @Override
	    public void deleteGroup(Group g) {
	        entityManager.remove(entityManager.contains(g) ? g : entityManager.merge(g));
	    }


	    public void updatePerson(Person p) {
	        entityManager.merge(p);
	    }

	    public void updateGroup(Group g) {
	        entityManager.merge(g);
	    }

	}

