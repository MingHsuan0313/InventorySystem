package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.AcquisitionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquisitionStatusRepository extends CrudRepository<AcquisitionStatus, Integer> {
}
