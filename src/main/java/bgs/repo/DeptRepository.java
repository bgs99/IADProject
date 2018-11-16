package bgs.repo;
import bgs.model.Dept;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeptRepository extends CrudRepository<Dept, Integer> {
    Dept findById(int id);
    List<Dept> findAllByParent(Dept parent);
}
