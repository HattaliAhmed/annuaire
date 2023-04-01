package mybootapp.repo;

import java.util.Collection;

import mybootapp.model.Group;
import mybootapp.model.Person;

public interface IDirectoryDao {

   // récupérer tous les groupes
   Collection<Group> findAllGroups();

   // récupérer toutes les personnes
   Collection<Person> findAllPersons();
   
   // récupérer toutes les personnes d'un groupe donné
   Collection<Person> findPersonsByGroup(Group group);

   // lire une personne par son identifiant
   Person findPersonById(long id);

   // lire un groupe et ses personnes par son identifiant
   Group findGroupById(long id);
   
   // récupérer un groupe par son nom
   Group findGroupByName(String name);

   // ajouter ou mettre à jour une personne
   void savePerson(Person person);

   // ajouter ou mettre à jour un groupe
   void saveGroup(Group group);

   // supprimer une personne
   void deletePerson(Person person);

   // supprimer un groupe
   void deleteGroup(Group group);

}
