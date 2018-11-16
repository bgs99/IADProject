package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ПСИХПОРТРЕТ")
public class Portrait implements Serializable {
    @Id @JoinColumn(name = "АГЕНТ", referencedColumnName = "ИД")
    private Agent agent;
    @Column(name = "АГРЕССИВНОСТЬ")
    private Double aggression;
    @Column(name = "ПОСЛУШАНИЕ")
    private Double loyalty;
    @Column(name = "ХАРИЗМА")
    private Double charisma;
    protected Portrait(){}

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
