package ntu.csie.selab.inventorysystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.HierarchyC2Sc;

@Repository
public interface HierarchyC2ScRepository extends CrudRepository<HierarchyC2Sc, Integer> {
    @Query(value = "SELECT h.subcategory FROM HierarchyC2Sc h WHERE h.category = :category")
    List<Object> findByCategory(@Param("category") Category category);

    @Query(value = "SELECT COUNT(h) FROM HierarchyC2Sc h WHERE h.category = :category")
    Integer countByCategory(@Param("category") Category category);
}
