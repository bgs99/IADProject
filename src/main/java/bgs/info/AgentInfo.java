package bgs.info;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.Person;
<<<<<<< HEAD
import org.springframework.stereotype.Component;
=======
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb

public class AgentInfo{
    public String name;
    public int id;
    public String deptName;
    public int deptId;
    public Integer salary;
    public int level;
    public boolean free;
<<<<<<< HEAD
    public double trust;
    public String imgPath = null;//TODO add to DB
=======
    public String imgPath = null;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
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
<<<<<<< HEAD
        free = true;//TODO find that
        trust = 50;//TODO THAT TOO
=======
        imgPath = p.getImage();
        free = true;//TODO find that
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb

    }
}