package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "ItemHistoryEvent")
public class ItemHistoryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "event")
    private String event;

    public Integer getId() {
        return id;
    }
    public String getEvent() {
        return event;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    @Override
    public String toString() {
        return event;
    }
}
