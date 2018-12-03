package bgs.info;


import bgs.model.SupportRequest;


public class SupportRequestInfo{
    public int id;
    public int mission;
    public Integer soldiers;
    public String data;
    public Integer transport;
    public Integer weapon;
    public boolean seen;
    public SupportRequestInfo(SupportRequest r){
        this.id = r.getId();
        this.mission = r.getMission().getId();
        this.soldiers = r.getSoldiers();
        this.data = r.getData();
        this.transport = r.getTransport() == null ? null : r.getTransport().getId();
        this.weapon = r.getWeapon() == null ? null : r.getWeapon().getId();
    }
}
