package bgs.repo;
import bgs.model.Organisation;
import bgs.model.People;
import bgs.model.Target;
import org.springframework.data.repository.CrudRepository;

public interface TargetRepository extends CrudRepository<Target, Integer> {
    Target findById(int id);
    Target findByPerson(People person);
    Target findByOrganisation(Organisation organisation);
}
