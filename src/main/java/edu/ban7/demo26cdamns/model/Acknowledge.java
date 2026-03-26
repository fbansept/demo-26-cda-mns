package edu.ban7.demo26cdamns.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.demo26cdamns.view.AcknowledgeView;
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

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Acknowledge {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "user_id")
        Integer userId;
        @Column(name = "skill_id")
        Integer skillId;
    }

    @EmbeddedId
    private Key id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonView(AcknowledgeView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected AppUser user;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    @JsonView(AcknowledgeView.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Skill skill;

    @JsonView(AcknowledgeView.class)
    protected int level;

}
