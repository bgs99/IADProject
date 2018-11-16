package repo;
import model.Dept;
import model.Organisation;
import model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeptRepository extends CrudRepository<Dept, Integer> {
    Dept findById(int id);
    List<Dept> findAllByParent(Dept parent);
}
