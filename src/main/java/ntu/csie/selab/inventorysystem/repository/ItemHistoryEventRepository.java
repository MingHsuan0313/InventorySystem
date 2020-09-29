package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.ItemHistoryEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemHistoryEventRepository extends CrudRepository<ItemHistoryEvent, Integer> {}
