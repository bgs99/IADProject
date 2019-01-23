package bgs.info;

import bgs.model.Agent;
import bgs.model.Dept;

import java.util.stream.Stream;

public class LoginInfo {
    public int id;
    public String role;
    public int level;
    public int deptId;
    public String[] types;
    public Integer currentMission;
    public Stream<String> rights;
    public LoginInfo(int id, Dept d, Agent a, Stream<String> r, Integer mi) {
        this.id = id;
        rights = r;
        role = d.toString();
        level = a.getLevel();
        deptId = d.getId();
        types = new String[]{"Выгулять собаку", "Устранение утечки"};
        currentMission = mi;
    }
}
