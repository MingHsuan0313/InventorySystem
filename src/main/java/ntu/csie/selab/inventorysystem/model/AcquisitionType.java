package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "AcquisitionType")
public class AcquisitionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type")
    private String type;

    public Integer getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
}
