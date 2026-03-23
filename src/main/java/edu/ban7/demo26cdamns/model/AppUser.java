package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.AppUserView;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    public interface OnUpdate {};
    public interface OnCreate {};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AppUserView.class)
    protected Integer id;

    @Column(nullable = false, unique = true) //l'email en bdd est obligatoire et unique
    @NotBlank(groups = {OnCreate.class}) //vérifie que la valeur n'est ni null ni vide ("")
    @Email(groups = {OnCreate.class}) //vérifie que la valeur a un format d'email ("a@a")
    protected String email;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class})
    protected String password;

    //note : plusieurs utilisateurs peuvent ne pas avoir de pseudo
    // mais si un pseudo est fournis il doit etre unique, > 5 caractères et <= 20 caractères
    @Column(length = 20, unique = true) //optimisation/definition de la bdd (+ harmoniser d'autre backend)
    @Length(min = 5, max = 20, groups = {OnCreate.class, OnUpdate.class})
    @JsonView({AppUserView.class, ComponentView.class})
    protected String pseudo;

    @ManyToOne(optional = false)
    @JsonView(AppUserView.class)
    protected Role role;

    @OneToMany(mappedBy = "loaner")
    @JsonView(AppUserView.class)
    List<Component> components = new ArrayList<>();

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo.toLowerCase();
    }
}
