package bgs.repo;
import bgs.model.Mission;
import bgs.model.Organisation;
import bgs.model.People;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MissionRepository extends CrudRepository<Mission, Integer> {
    Mission findById(int id);
    @Query("select m from Mission as m join m.target as t join t.person as p where p = ?1 and m.status = 'Выполнена'")
    Mission findFinishedByPerson(People person);
    @Query("select m from Mission as m join m.target as t join t.organisation as o where o = ?1 and m.status = 'Выполнена'")
    Mission findFinishedByOrganisation(Organisation organisation);
    @Query("select m from Mission as m where m.status <> 'Выполнена' ")
    List<Mission> findUnfinished();
    List<Mission> findAllByStatusIn(List<String> statuses);
}
