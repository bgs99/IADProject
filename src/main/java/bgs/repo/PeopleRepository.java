package bgs.repo;
import bgs.model.People;
import bgs.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends CrudRepository<People, Integer> {

    People findById(int id);
    List<People> findAllByNameAndSurname(String name, String surname);
    List<People> findAllByLocationOrderByDangerDesc(Place location);
    List<People> findAllBySex(Character sex);
    @Query("select avg(p.danger) from People as p where p.location = ?1")
    Double getDanger(Place location);
}
