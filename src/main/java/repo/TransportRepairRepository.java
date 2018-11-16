package repo;

import model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepairRepository extends CrudRepository<model.TransportRepair, Integer> {
    TransportRepair findById(int id);
    List<TransportRepair> findAllByResponisble(Agent agent);
    @Query("select r from TransportRepair as r where r.ready > current_timestamp")
    List<TransportRepair> findUnfinished();
    @Query("select r from TransportRepair as r join r.transport as w where w = ?1 and r.ready > current_timestamp ")
    List<TransportRepair> findAllInRepair(Transport transport);
}
