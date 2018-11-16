package bgs.repo;
import bgs.model.Agent;
import bgs.model.Portrait;
import org.springframework.data.repository.CrudRepository;

public interface PortraitRepository extends CrudRepository<Portrait, Integer> {
    Portrait findByAgent(Agent agent);
}
