package jee.annuaire.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "groupe",
        cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    @ToString.Exclude  // pour éviter les boucles
    private Collection<Person> members;

    public void addMember(Person person) {
        members.add(person);
        person.setGroupe(this);
    }
}
