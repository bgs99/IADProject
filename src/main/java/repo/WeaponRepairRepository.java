package repo;
import model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WeaponRepairRepository extends CrudRepository<WeaponRepair, Integer> {
    WeaponRepair findById(int id);
    List<WeaponRepair> findAllByResponisble(Agent agent);
    @Query("select r from WeaponRepair as r where r.ready > current_timestamp")
    List<WeaponRepair> findUnfinished();
    @Query("select r from WeaponRepair as r join r.weapon as w where w = ?1 and r.ready > current_timestamp ")
    List<WeaponRepair> findAllInRepair(Weapon weapon);
}
