package repo;
import model.Agent;
import model.InfoRequest;
import model.Mission;
import model.SupportRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportRequestRepository extends CrudRepository<SupportRequest, Integer> {
    SupportRequest findById(int id);
    List<SupportRequest> findAllBySeenIsFalse();
    List<SupportRequest> findAllByMission(Mission mission);
}
