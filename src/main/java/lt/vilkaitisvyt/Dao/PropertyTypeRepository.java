package lt.vilkaitisvyt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.vilkaitisvyt.Model.PropertyType;

@Repository
public interface PropertyTypeRepository extends JpaRepository <PropertyType, Long>{

}
