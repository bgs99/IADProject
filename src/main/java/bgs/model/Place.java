package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "АДМИНИСТРАТИВНЫЕ_ЕДИНИЦЫ")
public class Place implements Serializable {

    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @Column(name = "ИМЯ")
    private String name;
    @JoinColumn(name = "ПРИНАДЛЕЖИТ", referencedColumnName = "ИД")
    @ManyToOne
    private Place parent;
    @Column(name = "НАСЕЛЕНИЕ")
    private Integer population;
    @Column(name = "УРОВЕНЬ")
    private Integer level;
    protected Place(){}
    @Override
    public String toString(){
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public Place getParent() {
        return parent;
    }
    public Integer getLevel(){
        return level;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
