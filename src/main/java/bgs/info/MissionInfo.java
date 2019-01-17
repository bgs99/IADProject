package bgs.info;

import bgs.model.Mission;
import bgs.model.Team;
import bgs.repo.TeamRepository;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Stream;

public class MissionInfo {

    public int id;
    public int targetId;
    public String targetName;
    public String type;
    public String status;
    public String desc;
    public double danger;
    public Pair<Integer, String> location;
    public Stream<Pair<AgentInfo, WeaponInfo>> team;
    public Stream<TransportInfo> transport;
    public int minimalTeam;
    public int responsible;
    public MissionInfo(Mission m, TeamRepository teams){
        id = m.getId();
        responsible = m.getResponsible().getId();
        desc = m.getDescription();
        status = m.getStatus();
        type = m.getType().getName();
        minimalTeam = m.getType().getSize();
        TargetInfo target = new TargetInfo(m.getTarget());
        targetId = target.id;
        targetName = target.name;
        danger = target.danger;
        location = target.location;
        List<Team> ts = teams.findAllByMission(m);
        team = ts.stream().map(t -> Pair.of(new AgentInfo(t.getAgent()), new WeaponInfo(t.getWeapon())));
        transport = ts.stream().filter(t -> t.getTransport() != null).map(t -> new TransportInfo(t.getTransport()));
    }
}
