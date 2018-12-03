package bgs.info;

import bgs.model.Repair;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;

public class RepairInfo{
    public Pair<Integer, String> resp;
    public String name;
    public int id;
    public String condition;
    public Timestamp begin, end;
    public RepairInfo(Repair r){
        resp = r.getResponisble() != null ? Pair.of(r.getResponisble().getId(), r.getResponisble().getName()) : null;
        name = r.getName();
        condition = "OK";
        begin = r.getBegin();
        end = r.getReady();
        id = r.getId();
    }
}