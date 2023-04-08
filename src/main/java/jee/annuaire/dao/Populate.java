package jee.annuaire.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import jee.annuaire.model.Groupe;
import jee.annuaire.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Populate {

  @Autowired
  private DirectoryDao directoryDao;

  private static final int NB_GROUPES = 10;
  private static final int NB_PERSONNES_GROUPE = 10;

  private List<String> noms;

  @PostConstruct
  @Transactional
  public void populate() {
    try {
      noms = readNamesFromFile("src/main/resources/names.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    for (int i = 0; i < NB_GROUPES; i++) {
      Groupe groupe = new Groupe();
      groupe.setName("Groupe " + (i + 1));

      List<Person> personnes = new ArrayList<>();
      for (int j = 0; j < NB_PERSONNES_GROUPE; j++) {
        Person personne = generateRandomPerson();
        personne.setGroupe(groupe);
        personnes.add(personne);
      }

      groupe.setMembers(personnes);
      directoryDao.saveGroup(groupe);
    }
  }

  @Transactional
  Person generateRandomPerson() {
    String nom = noms.get((int) (Math.random() * noms.size()));
    String prenom = noms.get((int) (Math.random() * noms.size()));

    LocalDate birthDate = LocalDate.ofEpochDay((long) (Math.random() * 365 * 100));
    String email = prenom.toLowerCase() + "." + nom.toLowerCase() + "@example.com";
    String website = "http://" + prenom.toLowerCase() + nom.toLowerCase() + ".com";
    String password = "password";

    Person personne = new Person();
    personne.setFirstName(prenom);
    personne.setLastName(nom);
    personne.setBirthDate(birthDate);
    personne.setEmail(email);
    personne.setWebsite(website);
    personne.setPassword(password);

    directoryDao.savePerson(personne);

    return personne;
  }

  private List<String> readNamesFromFile(String fileName) throws FileNotFoundException {
    List<String> noms = new ArrayList<String>();
    Scanner scanner = new Scanner(new File(fileName));

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (!line.trim().isEmpty()) {
        noms.add(line.trim());
      }
    }

    scanner.close();
    return noms;
  }
}
