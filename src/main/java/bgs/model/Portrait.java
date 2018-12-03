package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ПСИХ_ПОРТРЕТ")
public class Portrait implements Serializable {
    @Id @GeneratedValue @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "АГЕНТ", referencedColumnName = "ИД")
    private Agent agent;
    @Column(name = "АГРЕССИВНОСТЬ")
    private Double aggression;
    @Column(name = "ПОСЛУШАНИЕ")
    private Double loyalty;
    @Column(name = "ХАРИЗМА")
    private Double charisma;
    protected Portrait(){}

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setAggression(Double aggression) {
        this.aggression = aggression;
    }

    public void setCharisma(Double charisma) {
        this.charisma = charisma;
    }

    public void setLoyalty(Double loyalty) {
        this.loyalty = loyalty;
    }

    public Agent getAgent() {
        return agent;
    }

    public Double getAggression() {
        return aggression;
    }

    public Double getCharisma() {
        return charisma;
    }

    public Double getLoyalty() {
        return loyalty;
    }
}
