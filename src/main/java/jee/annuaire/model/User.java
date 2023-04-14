package jee.annuaire.model;
import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String lastName;
    private String firstName;
}