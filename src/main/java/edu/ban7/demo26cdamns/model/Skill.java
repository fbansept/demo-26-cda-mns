package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.ComponentView;
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
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 20,nullable = false, unique = true)
    @NotBlank
    @Length(min = 3, max = 20)
    @JsonView(ComponentView.class)
    protected String name;

//    @ManyToMany(mappedBy = "tags")
//    protected List<Component> components;

}
