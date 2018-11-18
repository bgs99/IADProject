package bgs.repo;
import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.Target;
import org.springframework.data.repository.CrudRepository;

public interface TargetRepository extends CrudRepository<Target, Integer> {
    Target findById(int id);
    Target findByPerson(Person person);
    Target findByOrganisation(Organisation organisation);
}
