package bgs.info;


import bgs.model.SupportRequest;
import org.springframework.data.util.Pair;


public class SupportRequestInfo{
    public int id;
    public Pair<Integer, String> mission;
    public Integer soldiers;
    public String data;
    public Pair<Integer, String> transport;
    public Pair<Integer, String> weapon;
    public boolean seen;
    public SupportRequestInfo(SupportRequest r){
        this.id = r.getId();
        this.mission = Pair.of(r.getMission().getId(), new TargetInfo(r.getMission().getTarget()).name);
        this.soldiers = r.getSoldiers();
        this.data = r.getData();
        this.transport = r.getTransport() == null ? null : Pair.of(r.getTransport().getId(), r.getTransport().toString());
        this.weapon = r.getWeapon() == null ? null : Pair.of(r.getWeapon().getId(), r.getWeapon().toString());
    }
}
