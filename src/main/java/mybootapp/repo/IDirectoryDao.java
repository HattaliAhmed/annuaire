package mybootapp.repo;

import java.util.Collection;

import org.springframework.stereotype.Service;

import mybootapp.model.Groupe;
import mybootapp.model.Person;

@Service
public interface IDirectoryDao {

   // récupérer tous les groupes
   Collection<Groupe> findAllGroups();

   // récupérer toutes les personnes
   Collection<Person> findAllPersons();
   
   // récupérer toutes les personnes d'un groupe donné
   Collection<Person> findPersonsByGroup(Groupe groupe);

   // lire une personne par son identifiant
   Person findPersonById(long id);

   // lire un groupe et ses personnes par son identifiant
   Groupe findGroupById(long id);
   
   // récupérer un groupe par son nom
   Groupe findGroupByName(String name);

   // ajouter ou mettre à jour une personne
   void savePerson(Person person);

   // ajouter ou mettre à jour un groupe
   void saveGroup(Groupe groupe);

   // supprimer une personne
   void deletePerson(Person person);

   // supprimer un groupe
   void deleteGroup(Groupe groupe);

}
