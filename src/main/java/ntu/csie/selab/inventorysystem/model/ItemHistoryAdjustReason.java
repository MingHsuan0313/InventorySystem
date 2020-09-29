package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "ItemHistoryAdjustReason")
public class ItemHistoryAdjustReason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "reason")
    private String reason;

    public Integer getId() {
        return id;
    }
    public String getReason() {
        return reason;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    @Override
    public String toString() {
        return reason;
    }
}
