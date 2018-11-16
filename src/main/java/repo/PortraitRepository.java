package repo;
import model.Agent;
import model.Organisation;
import model.Place;
import model.Portrait;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PortraitRepository extends CrudRepository<Portrait, Integer> {
    Portrait findByAgent(Agent agent);
}
