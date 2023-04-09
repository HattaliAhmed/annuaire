package jee.annuaire.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String website;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String password;

    @ManyToOne(optional = true)
    @ToString.Exclude // afin d'éviter les boucles
    private Groupe groupe;
}
