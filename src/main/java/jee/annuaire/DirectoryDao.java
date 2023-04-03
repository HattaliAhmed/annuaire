package jee.annuaire;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class DirectoryDao implements IDirectoryDao {
	
		@PersistenceContext
		private EntityManager entityManager;
	    public Collection<Groupe> findAllGroups() {
	        TypedQuery<Groupe> query = entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class);
	        return query.getResultList();
	    }

	    public Collection<Person> findAllPersons() {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
	        return query.getResultList();
	    }
	    
	    public Collection<Person> findPersonsByGroup(Groupe groupe) {
	        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p JOIN p.groupe g WHERE g = :groupe", Person.class);
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

