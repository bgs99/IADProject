package repo;
import model.Place;
import model.Target;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Integer> {
    Place findById(int id);
    List<Place> findAllByParent(Place parent);
}
