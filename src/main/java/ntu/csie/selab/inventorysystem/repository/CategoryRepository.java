package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query(value = "SELECT COUNT(c) FROM Category c WHERE c.name = :name")
    Integer CountByName(@Param("name") String name);
    @Query(value = "SELECT COUNT(c) FROM Category c WHERE c.name = :name AND NOT c.id = :id")
    Integer CountByName(@Param("name") String name, @Param("id") Integer id);

    @Query(value = "SELECT COUNT(c) FROM Category c WHERE c.tag = :tag")
    Integer CountByTag(@Param("tag") String tag);
    @Query(value = "SELECT COUNT(c) FROM Category c WHERE c.tag = :tag AND NOT c.id = :id")
    Integer CountByTag(@Param("tag") String tag, @Param("id") Integer id);
}
