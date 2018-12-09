package bgs.repo;

import bgs.model.Agent;
import bgs.model.Mission;
import bgs.model.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends CrudRepository<Team, Integer> {
    List<Team> findAllByMission(Mission mission);
    List<Team> findAllByAgent(Agent agent);
    @Query("select t from Team as t join t.agent as a join t.mission as m where a = ?1 and m.status = 'Выполняется'")
    Optional<Team> findActiveByAgent(Agent agent);
}