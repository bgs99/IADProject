package repo;
import model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer> {
    Report findById(int id);
    List<Report> findAllBySubject(Agent subject);
    List<Report> findAllByMission(Mission mission);
}
