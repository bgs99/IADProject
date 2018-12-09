package bgs.repo;

import bgs.model.Agent;
import bgs.model.Mission;
import bgs.model.Organisation;
import bgs.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends CrudRepository<Mission, Integer> {
    Mission findById(int id);
    @Query("select count(m) from Mission as m join m.target as t join t.person as p where p = ?1 and m.status = 'Выполнена'")
    int countFinishedByPerson(Person person);
    @Query("select count(m) from Mission as m join m.target as t join t.organisation as o where o = ?1 and m.status = 'Выполнена'")
    int countFinishedByOrganisation(Organisation organisation);
    @Query("select m from Mission as m where m.status <> 'Выполнена' ")
    List<Mission> findUnfinished();
    List<Mission> findAllByStatusIn(List<String> statuses);

    @Query("select m from Mission as m, Portrait as p join m.type as t where p.agent = ?1 and t.charisma <= p.charisma and t.loyalty <= p.loyalty and t.aggression <= p.aggression and m.status = 'Создана'")
    List<Mission> findAcceptable(Agent a);


    @Query("select m from Mission as m where m.status <> 'Выполнена' and m.responsible = ?1")
    Optional<Mission> findActiveByResponsible(Agent a);
}
