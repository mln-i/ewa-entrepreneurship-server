package hva.ewa.Entrepreneurship.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "khanacademy")
public class KhanAcademyVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(max = 100)
    private String unique_id;

    @NotNull
    @Size(max = 300)
    private String title;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 300)
    private String image;

    @NotNull
    @Size(max = 300)
    private String url;

    @NotNull
    private Integer show_on_top;

    @NotNull
    private Integer show_hide;

    private String competences;

    @NotNull
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getShow_on_top() {
        return show_on_top;
    }

    public void setShow_on_top(Integer show_on_top) {
        this.show_on_top = show_on_top;
    }

    public Integer getShow_hide() {
        return show_hide;
    }

    public void setShow_hide(Integer show_hide) {
        this.show_hide = show_hide;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
