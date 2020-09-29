package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scid")
    private Category subcategory;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iid")
    private Item item;

    public Integer getId() {
        return id;
    }
    public Department getDepartment() {
        return department;
    }
    public Category getCategory() {
        return category;
    }
    public Category getSubcategory() {
        return subcategory;
    }
    public Item getItem() {
        return item;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    @Override
    public String toString() {
        return String.format("Inventory[id=%d, did=%d, cid=%d, scid=%d, iid=%d]",
                id, department.getId(), category.getId(), subcategory.getId(), item.getId());
    }
}
