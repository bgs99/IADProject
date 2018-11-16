package repo;
import model.Organisation;
import model.People;
import model.Target;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TargetRepository extends CrudRepository<Target, Integer> {
    Target findById(int id);
    Target findByPerson(People person);
    Target findByOrganisation(Organisation organisation);
}
