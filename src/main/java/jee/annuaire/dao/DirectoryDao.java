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

		@Override
		public Groupe findGroupByName(String name) {
			return entityManager.createQuery("SELECT g FROM Groupe g WHERE g.name = :name", Groupe.class)
					.setParameter("name", name).getSingleResult();
		}

		@Override
		public void saveGroup(Groupe groupe) {
			System.out.println("saveGroup: " + groupe.getName());
			groupe.getMembers().size();
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
		public List<Person> findPersonByName(String nom) {
			TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p WHERE p.lastName LIKE :nom OR p.firstName LIKE :nom", Person.class);
			query.setParameter("nom", "%" + nom + "%");
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

