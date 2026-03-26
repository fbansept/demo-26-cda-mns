package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.AppUserView;
import edu.ban7.demo26cdamns.view.ComponentView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Component {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ComponentView.class)
    protected Integer id;

    @Column(length = 20,nullable = false) //le nom en bdd est obligatoire
    @NotBlank //vérifie que la valeur n'est ni null ni vide ("")
    @Length(min = 3, max = 20)
    @JsonView({AppUserView.class , ComponentView.class})
    protected String name;

    @Column(length = 10,nullable = false, unique = true) //l'email en bdd est obligatoire et unique
    @NotBlank //vérifie que la valeur n'est ni null ni vide ("")
    @Length(min = 10, max = 10)
    @JsonView({AppUserView.class , ComponentView.class})
    protected String serialNumber;

    @Column(columnDefinition = "TEXT")
    @JsonView(ComponentView.class)
    protected String description;

    @ManyToOne
    @JsonView(ComponentView.class)
    //@JsonIgnore palliatif mais pas une bonne solution
    protected AppUser loaner;

    @ManyToMany
    @JoinTable(
            name = "tag_component",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonView(ComponentView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Tag> tags = new ArrayList<>();

}
