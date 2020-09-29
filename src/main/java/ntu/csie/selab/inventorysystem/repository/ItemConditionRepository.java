package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.ItemCondition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemConditionRepository extends CrudRepository<ItemCondition, Integer> {}
