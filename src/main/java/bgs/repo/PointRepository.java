package bgs.repo;

import bgs.model.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRepository extends CrudRepository<Point, Integer> {
    Point findById(int id);
}