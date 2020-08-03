package lt.vilkaitisvyt.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.vilkaitisvyt.Model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository <Owner, Long>{

}
