package ntu.csie.selab.inventorysystem.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    @Length(max = 45)
    @NotBlank
    private String name;
    @Column(name = "description")
    @Length(max = 200)
    private String description;
    @Column(name = "tag")
    @Length(max = 18)
    @NotBlank
    private String tag;
    @Column(name = "sub")
    private Boolean sub;

    public Category() {}
    public Category(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getTag() {
        return tag;
    }
    public Boolean getSub() {
        return sub;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setSub(Boolean sub) {
        this.sub = sub;
    }
    @Override
    public String toString() {
        return String.format("Category[id=%d, name='%s', description='%s', tag='%s', sub=%b]",
                id, name, description, tag, sub);
    }
}
