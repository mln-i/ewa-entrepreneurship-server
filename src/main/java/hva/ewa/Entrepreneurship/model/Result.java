package hva.ewa.Entrepreneurship.model;

import hva.ewa.Entrepreneurship.IdClass.ResultId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "result")
@IdClass(ResultId.class)
public class Result implements Serializable {

    @Id
    @NotNull
    private Integer competence_id;

    @Id
    @NotNull
    private Integer user_id;

    @NotNull
    private Double score;

    @Id
    @NotNull
    private String date_finished;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer competence_id) {
        this.competence_id = competence_id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getDate_finished() {
        return date_finished;
    }

    public void setDate_finished(String date_finished) {
        this.date_finished = date_finished;
    }
}
