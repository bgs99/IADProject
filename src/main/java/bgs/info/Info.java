package bgs.info;

import bgs.model.InfoRequest;
import org.springframework.data.util.Pair;

public class Info{
    public int id;
    public int level;
    public String body;
    public Pair<Integer, String> agent;
    public int agentLevel;
    public String resp;
    public String status;
    public String purp;
    public Info(InfoRequest i){
        id = i.getId();
        agent = Pair.of(i.getAgent().getId(), i.getAgent().getName());
        resp = i.getResponse();
        body = i.getRequest();
        status = i.getStatus();
        purp = i.getPurpose();
        agentLevel = i.getAgent().getLevel();
        level = i.getLevel();
    }
}