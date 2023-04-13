package jee.annuaire.model;

import java.util.Collection;
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

/**
 * Represents a group in the system.
 */
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
    @ToString.Exclude  // to avoid circular references
    private Collection<Person> members;

    /**
     * Adds a person as a member of the group.
     *
     * @param person The person to add
     */
    public void addMember(Person person) {
        members.add(person);
        person.setGroupe(this);
    }
}
