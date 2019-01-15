package bgs.repo;

import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.Place;
import bgs.model.Target;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TargetRepository extends CrudRepository<Target, Integer> {
    Target findById(int id);
    Target findByPerson(Person person);
    Target findByOrganisation(Organisation organisation);
    @Query("select distinct t from Target as t where t not in (select m.target from Mission as m where m.status = 'Выполнена')")
    List<Target> findAllActive();
    @Query("select t from Target as t join t.person as p where p.location = ?1")
    List<Target> findAllPeopleByLocation(Place location);
    @Query("select t from Target as t join t.organisation as o where o.location = ?1")
    List<Target> findAllOrgsByLocation(Place location);
}
