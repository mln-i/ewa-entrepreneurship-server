package hva.ewa.Entrepreneurship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "competence")
public class Competence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer competence_id;

    @NotNull
    @Size(max = 150)
    private String competence;

    @NotNull
    private String competence_type;

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer competence_id) {
        this.competence_id = competence_id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getCompetence_type() {
        return competence_type;
    }

    public void setCompetence_type(String competence_type) {
        this.competence_type = competence_type;
    }
}
