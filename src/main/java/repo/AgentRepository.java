package repo;
import model.Agent;
import model.People;
import model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends CrudRepository<Agent, Integer> {

    Agent findById(int id);
    Optional<Agent> findByPassport(People passport);
    List<Agent> findAllByDept(int dept);
    List<Agent> findAllByDeptAndLevelGreaterThanEqual(int dept, int minLevel);

    @Query("SELECT sum(payment) from Agent")
    Integer salarySum();

    @Query("SELECT a from Agent as a join a.passport as b where b.location = ?1")
    List<Agent> findAllByLocation(Place location);

    @Query("select b from Agent as a join a.passport as b where b.location = ?1")
    List<People> selectPeopleFromLocation(Place location);
}
