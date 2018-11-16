package bgs.repo;
import bgs.model.Weapon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeaponRepository extends CrudRepository<Weapon, Integer> {
    Weapon findById(int id);
    @Query("select w from Weapon as w where w.ready > 0")
    List<Weapon> findAllReady();
}
