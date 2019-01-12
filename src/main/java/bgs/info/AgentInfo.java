package bgs.info;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.Person;

public class AgentInfo{
    public String name;
    public int id;
    public String deptName;
    public int deptId;
    public Integer salary;
    public int level;
    public boolean free;
    public String imgPath = null;
    private AgentInfo(){}
    public AgentInfo(Agent agent){
        Person p = agent.getPassport();
        id = agent.getId();
        name = p.getName();
        Dept d = agent.getDept();
        deptName = d.toString();
        deptId = d.getId();
        salary = agent.getPayment();
        level = agent.getLevel();
        imgPath = p.getImage();
    }
}