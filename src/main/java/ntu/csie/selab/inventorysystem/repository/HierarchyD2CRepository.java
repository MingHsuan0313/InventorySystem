package ntu.csie.selab.inventorysystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.HierarchyD2C;

@Repository
public interface HierarchyD2CRepository extends CrudRepository<HierarchyD2C, Integer> {
    @Query(value = "SELECT h.category FROM HierarchyD2C h WHERE h.department = :department")
    List<Object> findByDepartment(@Param("department") Department department);
    @Query(value = "SELECT COUNT(h) FROM HierarchyD2C h WHERE h.department = :department")
    Integer countByDepartment(@Param("department") Department department);
    @Query(value = "SELECT h.id FROM HierarchyD2C h WHERE h.category = :category")
    Integer findIDByCID(@Param("category") Category category);
}
