package bgs.repo;
import bgs.model.Mission;
import bgs.model.MissionType;
import bgs.model.Organisation;
import bgs.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MissionTypeRepository extends CrudRepository<MissionType, Integer> {
    MissionType findById(int id);
}
