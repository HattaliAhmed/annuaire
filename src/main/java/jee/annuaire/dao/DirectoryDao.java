package jee.annuaire.dao;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.PasswordResetToken;
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
		public List<Groupe> findGroupByName(String name) {
			return entityManager.createQuery("SELECT g FROM Groupe g WHERE g.name LIKE :name", Groupe.class)
					.setParameter("name", name).getResultList();
		}

		public Collection<Person> findPersonInGroupByName(long groupId, String query) {
			return entityManager.createQuery("SELECT p FROM Person p WHERE p.groupe.id = :groupId AND (p.firstName LIKE :query OR p.lastName LIKE :query)  ORDER BY p.firstName, p.lastName ASC", Person.class)
					.setParameter("groupId", groupId)
					.setParameter("query", "%" + query + "%")
					.getResultList();
		}

		@Override
		public void saveGroup(Groupe groupe) {
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
					"SELECT p FROM Person p WHERE p.firstName LIKE :firstName OR p.lastName LIKE :lastName OR (p.firstName LIKE :lastName AND p.lastName LIKE :firstName) OR (p.firstName LIKE :firstName AND p.lastName LIKE :lastName) ORDER BY p.firstName, p.lastName ASC",
					Person.class
			);
			query.setParameter("firstName", "%" + firstName + "%");
			query.setParameter("lastName", "%" + lastName + "%");

			return query.getResultList();
		}

		@Override
		public Long findPersonByEmail(String email) {
			TypedQuery<Long> query = entityManager.createQuery(
					"SELECT p.id FROM Person p WHERE p.email = :email",
					Long.class
			);
			query.setParameter("email", email);
			try{
				Long id = query.getSingleResult();
				return id;
			} catch (NoResultException e) {
				return -1L;
			}
		}

		@Override
		public void savePerson(Person person) {
			entityManager.merge(person);
		}

		@Override
		public void deletePerson(Person person) {
			entityManager.remove(entityManager.contains(person) ? person : entityManager.merge(person));
		}

		@Override
		public PasswordResetToken findToken(String token) {
			// find the token that has his token field equals to the token parameter
			TypedQuery<PasswordResetToken> query = entityManager.createQuery(
					"SELECT p FROM PasswordResetToken p WHERE p.token = :token",
					PasswordResetToken.class
			);
			query.setParameter("token", token);
			try{
				PasswordResetToken passwordResetToken = query.getSingleResult();
				return passwordResetToken;
			} catch (NoResultException e) {
				return null;
			}
		}

		@Override
		public boolean verifyToken(String token) {
			PasswordResetToken passwordResetToken = findToken(token);
			if (passwordResetToken == null) {
				return false;
			}

			LocalDateTime now = LocalDateTime.now();
			if (passwordResetToken.getExpiryDate().isBefore(now)) {
				return false;
			}

			return true;
		}

		@Override
		public PasswordResetToken saveToken(PasswordResetToken token) {
			return entityManager.merge(token);
		}

		@Override
		public void deleteToken(PasswordResetToken token) {
			entityManager.remove(entityManager.contains(token) ? token : entityManager.merge(token));
		}

}

