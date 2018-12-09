package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ТРЕБОВАНИЯ_МИССИЙ")
public class MissionType implements Serializable {
    @Id @GeneratedValue @Column(name = "ТИП_МИССИИ")
    private int id;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @Column(name = "ХАРИЗМА")
    private double charisma;
    @Column(name = "АГРЕССИВНОСТЬ")
    private double aggression;
    @Column(name = "ПОСЛУШАНИЕ")
    private double loyalty;
    @Column(name = "РАЗМЕР_ОТРЯДА")
    private int size;

    protected MissionType(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCharisma() {
        return charisma;
    }

    public double getAggression() {
        return aggression;
    }

    public double getLoyalty() {
        return loyalty;
    }

    public int getSize() {
        return size;
    }
}
