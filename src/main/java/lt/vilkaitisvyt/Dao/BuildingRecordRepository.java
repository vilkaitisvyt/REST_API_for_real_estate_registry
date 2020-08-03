package lt.vilkaitisvyt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.vilkaitisvyt.Model.BuildingRecord;

@Repository
public interface BuildingRecordRepository extends JpaRepository <BuildingRecord, Long> {

}
