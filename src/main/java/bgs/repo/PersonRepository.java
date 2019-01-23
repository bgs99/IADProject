package bgs.repo;
import bgs.model.Person;
import bgs.model.Place;
import bgs.model.Target;
import bgs.model.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    Person findById(int id);
    List<Person> findAllByNameAndSurname(String name, String surname);
    List<Person> findAllByLocationOrderByDangerDesc(Place location);
    List<Person> findAllBySex(Character sex);
    @Query("select avg(p.danger) from Person as p where p.location = ?1 and p.alive = true")
    Double getDanger(Place location);
    Page<Person> findAll(Pageable p);
}
