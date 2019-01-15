package bgs.info;

import bgs.model.Place;
import bgs.model.Target;
import org.springframework.data.util.Pair;

public class TargetInfo {
    public int id;
    public String name;
    public double danger;
    public Pair<Integer, String> location;
    public boolean active = true;
    public boolean isPerson;
    public TargetInfo(Target target){
        id = target.getId();
        isPerson = target.isPerson();
        name = target.isPerson() ? target.getPerson().getName() : target.getOrganisation().getName();
        danger = target.isPerson() ? target.getPerson().getDanger() : target.getOrganisation().getDanger();
        Place p = target.isPerson() ? target.getPerson().getLocation() : target.getOrganisation().getLocation();
        location = Pair.of(p.getId(), p.getName());
    }
}