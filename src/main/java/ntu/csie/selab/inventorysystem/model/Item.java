package ntu.csie.selab.inventorysystem.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`condition`")
    private ItemCondition condition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aid")
    private Acquisition acquisition;

    public Item() {}
    public Item(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public ItemCondition getCondition() {
        return condition;
    }
    public Acquisition getAcquisition() {
        return acquisition;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }
    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }
    @Override
    public String toString() {
        return String.format("Item[id=%d, description='%s', quantity=%d, price=%2f, condition='%s', aid=%d]",
                id, description, quantity, price, condition.getCondition(), acquisition.getId());
    }

    public static class ItemValidation {
        @Length(max = 200)
        public String description;
        @Min(1)
        @NotNull
        public Integer quantity;
        @Min(0)
        public Double price;
        @NotBlank
        public String condition;
        public Integer aid;
        public Integer did;
        public Integer cid;
        public Integer scid;
        public String reason;
        public String comment;

        public void setDescription(String description){
            this.description = description;
        }

        public void setQuantity(Integer quantity){
            this.quantity = quantity;
        }

        public void setPrice(Double price){
            this.price = price;
        }

        public void setCondition(String condition){
            this.condition = condition;
        }

        public void setAid(Integer aid){
            this.aid = aid;
        }

        public void setDid(Integer did){
            this.did = did;
        }

        public void setCid(Integer cid){
            this.cid = cid;
        }        

        public void setScid(Integer scid){
            this.scid = scid;
        }

        public void setReason(String reason){
            this.reason = reason;
        }        

        public void setComment(String comment){
            this.comment = comment;
        }

        public Item toItem() {
            Item item = new Item();
            item.setDescription(description);
            item.setQuantity(quantity);
            item.setPrice(price);
            item.setAcquisition(aid == null ? null : new Acquisition(aid));
            return item;
        }
    }
}
