package bgs.info;

import bgs.model.Repair;
import bgs.model.WeaponRepair;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;

public class RepairInfo{
    public Pair<Integer, String> resp;
    public String name;
    public int id;
    public Long begin, end;
    public boolean isWeapon;
    public RepairInfo(Repair r){
        resp = r.getResponsible() != null ? Pair.of(r.getResponsible().getId(), r.getResponsible().getName()) : null;
        name = r.getName();
        begin = r.getBegin().getTime();
        end = r.getReady() == null ? null : r.getReady().getTime();
        id = r.getId();
        isWeapon = r instanceof WeaponRepair;
    }
}