package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcquisitionRepository extends CrudRepository<Acquisition, Integer> {
    /*@Query(value = "SELECT i.id FROM Item i INNER JOIN i.acquisition")
    List<Integer> getItemIdWithAcqusition();
    @Query(value = "SELECT i FROM Item i WHERE i.id = :id")
    List<Item> getItemsById(@Param("id") Integer id);*/

    @Query(value = "SELECT i FROM Item i INNER JOIN i.acquisition ON i.acquisition.id = :id")
    List<Item> getItemIdWithAcqusition(@Param("id") Integer id);
}
