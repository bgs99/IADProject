package bgs.info;

import bgs.model.RegistryEntry;
import bgs.model.Target;
import bgs.repo.TargetRepository;
import org.springframework.data.util.Pair;

public class Registry{
    public String name;
    public int id;
    public Pair<Integer, String> location;
    public double danger;
    public boolean status;
    public boolean isPerson;
    public Integer target;
    public Registry(RegistryEntry r, boolean alive, boolean isPerson, Integer target){
        name = r.getName();
        id = r.getId();
        location = Pair.of(r.getLocation().getId(), r.getLocation().getName());
        danger = r.getDanger();
        status = alive;
        this.isPerson = isPerson;
        this.target = target;
    }
}
