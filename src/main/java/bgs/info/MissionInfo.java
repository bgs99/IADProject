package bgs.info;

import bgs.model.Mission;
import bgs.model.Place;
import bgs.model.Target;
import bgs.model.Team;
import bgs.repo.TeamRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
=======
import org.springframework.data.util.Pair;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb

import java.util.List;
import java.util.stream.Stream;

public class MissionInfo {

    public int id;
    public String targetName;
    public String type;
    public Pair<Integer, String> location;
    public Stream<Pair<AgentInfo, WeaponInfo>> team;
    public Stream<TransportInfo> transport;
    public MissionInfo(Mission m, TeamRepository teams){
        id = m.getId();
        type = m.getType().getName();
        Target tg = m.getTarget();
<<<<<<< HEAD
        targetName = tg.isPerson() ? tg.getPerson().toString() : tg.getOrganisation().toString();
=======
        targetName = tg.isPerson() ? tg.getPerson().getName() : tg.getOrganisation().toString();
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
        Place p = tg.isPerson() ? tg.getPerson().getLocation() : tg.getOrganisation().getLocation();
        location = Pair.of(p.getId(), p.getName());
        List<Team> ts = teams.findAllByMission(m);
        team = ts.stream().map(t -> Pair.of(new AgentInfo(t.getAgent()), new WeaponInfo(t.getWeapon())));
        transport = ts.stream().filter(t -> t.getTransport() != null).map(t -> new TransportInfo(t.getTransport()));
    }
}
