package bgs.repo;
import bgs.model.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Integer> {
    Place findById(int id);
    List<Place> findAllByParent(Place parent);
    List<Place> findAllByName(String name);
}
