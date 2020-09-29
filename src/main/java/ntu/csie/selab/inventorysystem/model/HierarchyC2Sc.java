package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "CategorySubcategory")
public class HierarchyC2Sc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Category category;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scid")
    private Category subcategory;

    public Integer getId() {
        return id;
    }
    public Category getCategory() {
        return category;
    }
    public Category getSubcategory() {
        return subcategory;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }
    @Override
    public String toString() {
        return String.format("Hierarchy[id=%d, cid=%d, scid=%d]", id, category.getId(), subcategory.getId());
    }
}
