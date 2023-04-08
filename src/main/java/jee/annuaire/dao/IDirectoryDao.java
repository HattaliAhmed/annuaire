package jee.annuaire.dao;

import java.util.Collection;
import java.util.List;
import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import org.springframework.stereotype.Service;

@Service
public interface IDirectoryDao {

   /////// Groupes //////

   /**
    * Récupérer tous les groupes de l'annuaire.
    * @return
    */
   Collection<Groupe> findAllGroups();

   /**
    * Récupérer le groupe dont l'identifiant est passé en paramètre.
    * @param id
    * @return
    */
   Groupe findGroupById(long id);


    /**
     * Récupérer le groupe dont le nom est passé en paramètre.
     * @param name
     * @return
     */
    Groupe findGroupByName(String name);

    /**
     * Sauvegarder un groupe dans l'annuaire. S'il existe déjà, le mettre à jour.
     * @param groupe
     */
    void saveGroup(Groupe groupe);

    /**
     * Supprimer un groupe de l'annuaire.
     * @param groupe
     */
    void deleteGroup(Groupe groupe);

  /////// Personnes //////

  /**
     * Récupérer la personne dont l'identifiant est passé en paramètre.
     * @param id
     * @return
     */
    Person findPersonById(long id);

    /**
      * Récupérer la personne dont le nom est passé en paramètre.
      * @param nom
      * @return
      */
    List<Person> findPersonByName(String nom);

   /**
    * Sauvegarder une personne dans l'annuaire. Si elle existe déjà, la mettre à jour.
    * @param person
    */
   void savePerson(Person person);

    /**
      * Supprimer une personne de l'annuaire.
      * @param person
      */
   void deletePerson(Person person);

}
