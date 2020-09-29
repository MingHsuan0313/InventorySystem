package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.AcquisitionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquisitionTypeRepository extends CrudRepository<AcquisitionType, Integer> {
}
