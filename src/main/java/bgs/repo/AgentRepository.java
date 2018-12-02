package bgs.repo;
import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.Person;
import bgs.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends CrudRepository<Agent, Integer> {

    Agent findByTelegram(int tg);
    Agent findById(int id);
    Optional<Agent> findByPassport(Person passport);
    List<Agent> findAllByDept(Dept dept);
    List<Agent> findAllByDeptAndLevelGreaterThanEqual(Dept dept, int minLevel);

    @Query("SELECT COUNT(a) from Agent as a join a.passport as b where b.location = ?1 and a.level <= ?2")
    int countAgentsByLocationAndLevel(Place location, int level);

    @Query("SELECT sum(payment) from Agent")
    Integer salarySum();

    @Query("SELECT a from Agent as a join a.passport as b where b.location = ?1 and a.level <= ?2")
    List<Agent> findAllByLocationAndLevel(Place location, int level);

    @Query("select b from Agent as a join a.passport as b where b.location = ?1 and a.level <= ?2")
    List<Person> selectPeopleFromLocationAndLevel(Place location, int level);
}
