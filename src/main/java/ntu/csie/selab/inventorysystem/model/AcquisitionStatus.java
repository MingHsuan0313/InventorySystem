package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "AcquisitionStatus")
public class AcquisitionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return status;
    }
}
