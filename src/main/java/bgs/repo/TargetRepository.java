package bgs.repo;
import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.Target;
import bgs.model.Mission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface TargetRepository extends CrudRepository<Target, Integer> {
    Target findById(int id);
    Target findByPerson(Person person);
    Target findByOrganisation(Organisation organisation);
    @Query("select distinct t from Target as t where t not in (select m.target from Mission as m where m.status = 'Выполнена')")
    List<Target> findAllActive();
}
