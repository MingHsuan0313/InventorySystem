package ntu.csie.selab.inventorysystem.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Department")
public class Department {
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
    @Column(name = "POSCode")
    @Length(max = 3)
    @NotBlank
    private String code;
    @Column(name = "tag")
    @Length(max = 18)
    @NotBlank
    private String tag;

    public Department() {}
    public Department(Integer id) {
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
    public String getCode() {
        return code;
    }
    public String getTag() {
        return tag;
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
    public void setCode(String code) {
        this.code = code;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    @Override
    public String toString() {
        return String.format("Department[id=%d, name='%s', description='%s', POSCode='%s', tag='%s']",
                id, name, description, code, tag);
    }
}
