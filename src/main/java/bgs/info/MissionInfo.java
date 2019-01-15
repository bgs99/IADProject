package bgs.info;

import bgs.model.Mission;
import bgs.model.Place;
import bgs.model.Target;
import bgs.model.Team;
import bgs.repo.TeamRepository;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Stream;

public class MissionInfo {

    public int id;
    public Target target;
    public String targetName;
    public String type;
    public String status;
    public String desc;
    public double danger;
    public Pair<Integer, String> location;
    public Stream<Pair<AgentInfo, WeaponInfo>> team;
    public Stream<TransportInfo> transport;
    public MissionInfo(Mission m, TeamRepository teams){
        id = m.getId();
        desc = m.getDescription();
        status = m.getStatus();
        type = m.getType().getName();
        target = m.getTarget();
        targetName = target.isPerson() ? target.getPerson().getName() : target.getOrganisation().getName();
        danger = target.isPerson() ? target.getPerson().getDanger() : target.getOrganisation().getDanger();
        Place p = target.isPerson() ? target.getPerson().getLocation() : target.getOrganisation().getLocation();
        location = Pair.of(p.getId(), p.getName());
        List<Team> ts = teams.findAllByMission(m);
        team = ts.stream().map(t -> Pair.of(new AgentInfo(t.getAgent()), new WeaponInfo(t.getWeapon())));
        transport = ts.stream().filter(t -> t.getTransport() != null).map(t -> new TransportInfo(t.getTransport()));
    }
}
