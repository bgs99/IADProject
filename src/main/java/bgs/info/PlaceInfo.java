package bgs.info;

import bgs.controllers.LoginManager;
import bgs.model.Place;
import bgs.repo.AgentRepository;
import bgs.repo.PersonRepository;
import bgs.repo.PlaceRepository;
import bgs.repo.TargetRepository;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PlaceInfo{
    public String name;
    public int id;
    public Integer parentId;
    public String parentName;
    public List<Pair<Integer, String>> units = new ArrayList<>();
    public double danger;
    public long population;
    public int cops;
    public int targets;
    public PlaceInfo(Place place, LoginManager manager,
                     AgentRepository agents, PersonRepository people, PlaceRepository places, TargetRepository targetsRep){

        name = place.getName();
        id = place.getId();

        Place parent = place.getLevel() > 0 ? place.getParent() : null;
        parentId = parent == null ? null : parent.getId();
        parentName = parent == null ? null : parent.getName();

        population = place.getPopulation();
        int level = manager.getLevel();
        cops = agents.countAgentsByLocationAndLevel(place, level);
        danger = people.getDanger(place);
        for(Place ps : places.findAllByParent(place)){
            units.add(Pair.of(ps.getId(), ps.getName()));
        }
        targets = 0;
    }
}