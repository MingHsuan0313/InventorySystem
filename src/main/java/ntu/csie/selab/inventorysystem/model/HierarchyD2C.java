package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;

@Entity
@Table(name = "DepartmentCategory")
public class HierarchyD2C {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private Department department;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cid")
    private Category category;

    public Integer getId() {
        return id;
    }
    public Department getDepartment() {
        return department;
    }
    public Category getCategory() {
        return category;
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
    // public int findIDByCategoryId(Integer categoryId) {
    //     for(int index = 0;index < this.category
    //     return 0;
    // }

    @Override
    public String toString() {
        return String.format("Hierarchy[id=%d, did=%d, cid=%d]", id, department.getId(), category.getId());
    }
}
