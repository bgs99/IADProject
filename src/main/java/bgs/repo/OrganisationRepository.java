package bgs.repo;
import bgs.model.Organisation;
import bgs.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {
    Organisation findById(int id);
    @Query("select avg(o.danger) from Organisation as o where o.location = ?1")
    Double getDanger(Place location);
    List<Organisation> findAllByLocationOrderByDangerDesc(Place location);
    List<Organisation> findAll();
}
