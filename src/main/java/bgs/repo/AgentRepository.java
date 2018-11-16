package bgs.repo;
import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.People;
import bgs.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends CrudRepository<Agent, Integer> {

    Agent findById(int id);
    Optional<Agent> findByPassport(People passport);
    List<Agent> findAllByDept(Dept dept);
    List<Agent> findAllByDeptAndLevelGreaterThanEqual(Dept dept, int minLevel);

    @Query("SELECT COUNT(a) from Agent as a join a.passport as b where b.location = ?1")
    int countAgentsByLocation(Place location);

    @Query("SELECT sum(payment) from Agent")
    Integer salarySum();

    @Query("SELECT a from Agent as a join a.passport as b where b.location = ?1")
    List<Agent> findAllByLocation(Place location);

    @Query("select b from Agent as a join a.passport as b where b.location = ?1")
    List<People> selectPeopleFromLocation(Place location);
}
