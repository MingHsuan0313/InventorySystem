package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.Inventory;
import ntu.csie.selab.inventorysystem.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    @Query(value = "SELECT i FROM Inventory i WHERE i.item = :item")
    Inventory findByItem(@Param("item") Item item);

    @Query(value = "SELECT i FROM Inventory i WHERE i.department = :department")
    List<Inventory> findSubtreeByHierarchy(@Param("department") Department department);
    @Query(value = "SELECT i FROM Inventory i WHERE i.department = :department AND i.category = NULL AND i.subcategory = NULL")
    List<Inventory> findLeafByHierarchy(@Param("department") Department department);
    @Query(value = "SELECT i FROM Inventory i WHERE i.department = :department AND i.category = :category")
    List<Inventory> findSubtreeByHierarchy(@Param("department") Department department, @Param("category") Category category);
    @Query(value = "SELECT i FROM Inventory i WHERE i.department = :department AND i.category = :category AND i.subcategory = NULL")
    List<Inventory> findLeafByHierarchy(@Param("department") Department department, @Param("category") Category category);
    @Query(value = "SELECT i FROM Inventory i WHERE i.department = :department AND i.category = :category AND i.subcategory = :subcategory")
    List<Inventory> findLeafByHierarchy(@Param("department") Department department, @Param("category") Category category, @Param("subcategory") Category subcategory);

    @Query(value = "SELECT COUNT(i) FROM Inventory i WHERE i.department = :department")
    Integer countByDepartment(@Param("department") Department department);
    @Query(value = "SELECT COUNT(i) FROM Inventory i WHERE i.category = :category")
    Integer countByCategory(@Param("category") Category category);
}
