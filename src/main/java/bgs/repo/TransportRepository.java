package bgs.repo;
import bgs.model.Transport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepository extends CrudRepository<Transport, Integer> {
    Transport findById(int id);
    @Query("select t from Transport as t where t.ready > 0")
    List<Transport> findAllReady();
    List<Transport> findAll();
}
