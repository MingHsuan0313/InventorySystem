package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.ItemHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemHistoryRepository extends CrudRepository<ItemHistory, Integer> {}
