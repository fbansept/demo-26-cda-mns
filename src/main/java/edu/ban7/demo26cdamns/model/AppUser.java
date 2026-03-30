package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.AcknowledgeView;
import edu.ban7.demo26cdamns.view.AppUserView;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AppUser {

    public interface OnUpdate {};
    public interface OnCreate {};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({AppUserView.class, AcknowledgeView.class})
    protected Integer id;

    @Column(nullable = false, unique = true) //l'email en bdd est obligatoire et unique
    @NotBlank(groups = {OnCreate.class}, message = "L'email ne peut pas être vide") //vérifie que la valeur n'est ni null ni vide ("")
    @Email(groups = {OnCreate.class}, message = "L'email est mal formé") //vérifie que la valeur a un format d'email ("a@a")
    @JsonView
    protected String email;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class}, message = "Le mot de passe ne peut pas être vide")
    @JsonView
    protected String password;

    //note : plusieurs utilisateurs peuvent ne pas avoir de pseudo
    // mais si un pseudo est fournis il doit etre unique, > 5 caractères et <= 20 caractères
    @Column(length = 20, unique = true) //optimisation/definition de la bdd (+ harmoniser d'autre backend)
    @Size(min = 5, max = 20, groups = {OnCreate.class, OnUpdate.class},  message = "Le pseudo doit être de 5 à 20 caractères")
    @JsonView({AppUserView.class, ComponentView.class, AcknowledgeView.Extra.class})
    protected String pseudo;

    @ManyToOne
    @JsonView(AppUserView.class)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Role role;

    @OneToMany(mappedBy = "loaner")
    @JsonView(AppUserView.class)
    List<Component> components = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Met en minuscule le pseudo
     * @param pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo.toLowerCase();
    }
}
