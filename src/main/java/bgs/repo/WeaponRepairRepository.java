package bgs.repo;

import bgs.model.Agent;
import bgs.model.Weapon;
import bgs.model.WeaponRepair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeaponRepairRepository extends CrudRepository<WeaponRepair, Integer> {
    WeaponRepair findById(int id);
    List<WeaponRepair> findAllByResponsible(Agent agent);
    @Query("select r from WeaponRepair as r where r.responsible is null or (r.responsible = ?1 and r.ready > current_timestamp)")
    List<WeaponRepair> findUnfinished(Agent a);
    @Query("select r from WeaponRepair as r join r.weapon as w where w = ?1 and r.ready > current_timestamp ")
    List<WeaponRepair> findAllInRepair(Weapon weapon);
}
