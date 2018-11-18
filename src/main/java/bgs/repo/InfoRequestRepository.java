package bgs.repo;
import bgs.model.Agent;
import bgs.model.InfoRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InfoRequestRepository extends CrudRepository<InfoRequest, Integer> {
    InfoRequest findById(int id);
    List<InfoRequest> findAllByAgent(Agent agent);
    @Query("select r from InfoRequest as r where r.response is null and r.level <= ?1")
    List<InfoRequest> findAllAvailable(int level);
    @Query("select r from InfoRequest as r where r.agent = ?1")
    List<InfoRequest> findVisible(Agent agent);
}
