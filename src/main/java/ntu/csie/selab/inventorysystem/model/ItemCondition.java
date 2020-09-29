package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "ItemCondition")
public class ItemCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "condition")
    private String condition;

    public Integer getId() {
        return id;
    }
    public String getCondition() {
        return condition;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    @Override
    public String toString() {
        return condition;
    }
}
