package repo;
import model.Agent;
import model.Dept;
import model.InfoRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfoRequestRepository extends CrudRepository<InfoRequest, Integer> {
    InfoRequest findById(int id);
    List<InfoRequest> findAllByAgent(Agent agent);
    @Query("select r from InfoRequest as r join Agent as a where r.response is null and r.level <= a.level and a = ?1")
    List<InfoRequest> findAllAvailable(Agent agent);
}
