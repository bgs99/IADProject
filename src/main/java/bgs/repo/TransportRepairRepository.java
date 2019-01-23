package bgs.repo;

import bgs.model.Agent;
import bgs.model.Transport;
import bgs.model.TransportRepair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepairRepository extends CrudRepository<bgs.model.TransportRepair, Integer> {
    TransportRepair findById(int id);
    List<TransportRepair> findAllByResponsible(Agent agent);
    @Query("select r from TransportRepair as r where r.responsible is null or (r.responsible = ?1 and r.ready > current_timestamp)")
    List<TransportRepair> findUnfinished(Agent a);
    @Query("select r from TransportRepair as r join r.transport as w where w = ?1 and r.ready > current_timestamp ")
    List<TransportRepair> findAllInRepair(Transport transport);
}
