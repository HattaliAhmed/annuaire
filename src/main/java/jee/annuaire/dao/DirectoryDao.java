package jee.annuaire.dao;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class DirectoryDao implements IDirectoryDao {
		@PersistenceContext
		private EntityManager entityManager;

		@Override
		public Collection<Groupe> findAllGroups() {
			return entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class).getResultList();
		}

		@Override
		public Groupe findGroupById(long id) {
			return entityManager.find(Groupe.class, id);
		}

		// same as above but returns a list
		@Override
		public List<Groupe> findGroupByName(String name) {
			return entityManager.createQuery("SELECT g FROM Groupe g WHERE g.name LIKE :name", Groupe.class)
					.setParameter("name", name).getResultList();
		}

		public Collection<Person> findPersonInGroupByName(long groupId, String query) {
			return entityManager.createQuery("SELECT p FROM Person p WHERE p.groupe.id = :groupId AND (p.firstName LIKE :query OR p.lastName LIKE :query)", Person.class)
					.setParameter("groupId", groupId)
					.setParameter("query", "%" + query + "%")
					.getResultList();
		}

		@Override
		public void saveGroup(Groupe groupe) {
			System.out.println("saveGroup: " + groupe.getName());
			entityManager.merge(groupe);
		}

		@Override
		public void deleteGroup(Groupe groupe) {
			entityManager.remove(entityManager.contains(groupe) ? groupe : entityManager.merge(groupe));
		}

		@Override
		public Person findPersonById(long id) {
			return entityManager.find(Person.class, id);
		}

		@Override
		public List<Person> findPersonByName(String name) {
			String[] parts = name.split("\\s+");
			String firstName = parts.length > 1 ? parts[0] : name;
			String lastName = parts.length > 1 ? parts[parts.length - 1] : name;

			TypedQuery<Person> query = entityManager.createQuery(
					"SELECT p FROM Person p WHERE p.firstName LIKE :firstName OR p.lastName LIKE :lastName OR (p.firstName LIKE :lastName AND p.lastName LIKE :firstName) OR (p.firstName LIKE :firstName AND p.lastName LIKE :lastName)",
					Person.class
			);
			query.setParameter("firstName", "%" + firstName + "%");
			query.setParameter("lastName", "%" + lastName + "%");

			return query.getResultList();
		}


		@Override
		public void savePerson(Person person) {
			System.out.println("savePerson: " + person.getLastName());
			entityManager.merge(person);
		}

		@Override
		public void deletePerson(Person person) {
			entityManager.remove(entityManager.contains(person) ? person : entityManager.merge(person));
		}
}

