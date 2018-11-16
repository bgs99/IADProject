package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "КОМАНДЫ")
@IdClass(Team.TeamKey.class)
public class Team implements Serializable {

    public class TeamKey implements Serializable{
        private Mission mission;
        private Agent agent;
    }

    @Id
    @JoinColumn(name = "МИССИЯ", nullable = false, referencedColumnName = "ИД")
    @ManyToOne
    private Mission mission;
    @Id
    @ManyToOne
    @JoinColumn(name = "АГЕНТ", nullable = false, referencedColumnName = "ИД")
    private Agent agent;
    @ManyToOne
    @JoinColumn(name = "ОРУЖИЕ", referencedColumnName = "ИД")
    private Weapon weapon;
    @ManyToOne
    @JoinColumn(name = "ТРАНСПОРТ", referencedColumnName = "ИД")
    private Transport transport;
    @ManyToOne
    @JoinColumn(name = "ПРИКРЫТИЕ", referencedColumnName = "ПАСПОРТ")
    private People cover;


    protected Team(){}
    public Team(Agent agent, Mission mission, Weapon weapon, Transport transport, People cover){
        this.agent = agent;
        this.mission = mission;
        this.weapon = weapon;
        this.transport = transport;
        this.cover = cover;
    }

    public Agent getAgent() {
        return agent;
    }

    public Transport getTransport() {
        return transport;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Mission getMission() {
        return mission;
    }

    public People getCover() {
        return cover;
    }
}
