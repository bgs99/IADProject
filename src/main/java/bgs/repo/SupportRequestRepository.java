package bgs.repo;
import bgs.model.Mission;
import bgs.model.SupportRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportRequestRepository extends CrudRepository<SupportRequest, Integer> {
    SupportRequest findById(int id);
    List<SupportRequest> findAllBySeenIsFalse();
    List<SupportRequest> findAllByMission(Mission mission);
}
