package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.AppUserView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleEnum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 20,nullable = false, unique = true) //l'email en bdd est obligatoire et unique
    @NotBlank //vérifie que la valeur n'est ni null ni vide ("")
    @Length(min = 3, max = 20)
    @JsonView(AppUserView.class)
    protected RoleNom name;

}

public enum RoleNom {

    SUPPLIER,
    USER,
    ADMIN
    
}


