package mybootapp.repo;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import mybootapp.model.Groupe;
import mybootapp.model.Person;

@Repository
@Transactional
public class DirectoryDao implements IDirectoryDao {
	
		@PersistenceContext
		private EntityManager entityManager;
		
	    private EntityManagerFactory factory = null;

	    @PostConstruct
	    public void init() {
	        factory = Persistence.createEntityManagerFactory("myBase");
	    }

	    @PreDestroy
	    public void close() {
	        if (factory != null) {
	            factory.close();
	        }
	    }


	    public Collection<Groupe> findAllGroups() {
	        TypedQuery<Groupe> query = entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class);
	        return query.getResultList();
	    }

	    public Collection<Person> findAllPersons() {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
	        return query.getResultList();
	    }
	    
	    public Collection<Person> findPersonsByGroup(Groupe groupe) {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p JOIN p.groups g WHERE g = :groupe", Person.class);
	        query.setParameter("groupe", groupe);
	        return query.getResultList();
	    }

	    public Person findPersonById(long id) {
	        return entityManager.find(Person.class, id);
	    }

	    public Groupe findGroupById(long id) {
	        return entityManager.find(Groupe.class, id);
	    }


	    public Groupe findGroupByName(String name) {
	        TypedQuery<Groupe> query = entityManager.createQuery("SELECT g FROM Groupe g WHERE g.name = :name", Groupe.class);
	        query.setParameter("name", name);
	        return query.getSingleResult();
	    }


	    public void savePerson(Person p) {
	        entityManager.persist(p);
	    }


	    public void saveGroup(Groupe g) {
	        entityManager.persist(g);
	    }


	    public void deletePerson(Person p) {
	        entityManager.remove(entityManager.contains(p) ? p : entityManager.merge(p));
	    }


	    public void deleteGroup(Groupe g) {
	        entityManager.remove(entityManager.contains(g) ? g : entityManager.merge(g));
	    }


	    public void updatePerson(Person p) {
	        entityManager.merge(p);
	    }

	    public void updateGroup(Groupe g) {
	        entityManager.merge(g);
	    }

	}

