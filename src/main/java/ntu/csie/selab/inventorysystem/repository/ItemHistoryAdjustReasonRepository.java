package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.ItemHistoryAdjustReason;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemHistoryAdjustReasonRepository extends CrudRepository<ItemHistoryAdjustReason, Integer> {}
