package bgs.info;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.Person;
import org.springframework.stereotype.Component;

public class AgentInfo{
    public String name;
    public int id;
    public String deptName;
    public int deptId;
    public Integer salary;
    public int level;
    public boolean free;
    public double trust;
    public String imgPath = null;//TODO add to DB
    private AgentInfo(){}
    public AgentInfo(Agent agent){
        Person p = agent.getPassport();
        id = agent.getId();
        name = p.toString();
        Dept d = agent.getDept();
        deptName = d.toString();
        deptId = d.getId();
        salary = agent.getPayment();
        level = agent.getLevel();
        free = true;//TODO find that
        trust = 50;//TODO THAT TOO

    }
}