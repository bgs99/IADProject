package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "КОМАНДЫ")
@IdClass(Team.TeamKey.class)
public class Team implements Serializable {

    public static class TeamKey implements Serializable{
        private int mission;
        private int agent;
        public TeamKey(){

        }
        @Override
        public boolean equals(Object o){
            if(!o.getClass().equals(this.getClass()))
                return false;
            TeamKey ot = (TeamKey)o;
            return ot.mission == mission && ot.agent == agent;
        }
        @Override
        public int hashCode(){
            return super.hashCode();
        }
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
    private Person cover;


    protected Team(){}
    public Team(Agent agent, Mission mission, Weapon weapon, Transport transport, Person cover){
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

    public Person getCover() {
        return cover;
    }
}
