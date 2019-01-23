package bgs.repo;

import bgs.model.Agent;
import bgs.model.Mission;
import bgs.model.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer> {
    Report findById(int id);
    List<Report> findAllBySubject(Agent subject);
    List<Report> findAllByMission(Mission mission);
    List<Report> findAllByAuthor(Agent agent);
    List<Report> findAll();
}
