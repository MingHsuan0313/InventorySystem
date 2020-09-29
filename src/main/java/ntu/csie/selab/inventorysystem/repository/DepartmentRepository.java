package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    @Query(value = "SELECT COUNT(d) FROM Department d WHERE d.name = :name")
    Integer CountByName(@Param("name") String name);
    @Query(value = "SELECT COUNT(d) FROM Department d WHERE d.name = :name AND NOT d.id = :id")
    Integer CountByName(@Param("name") String name, @Param("id") Integer id);

    @Query(value = "SELECT COUNT(d) FROM Department d WHERE d.tag = :tag")
    Integer CountByTag(@Param("tag") String tag);
    @Query(value = "SELECT COUNT(d) FROM Department d WHERE d.tag = :tag AND NOT d.id = :id")
    Integer CountByTag(@Param("tag") String tag, @Param("id") Integer id);
}
