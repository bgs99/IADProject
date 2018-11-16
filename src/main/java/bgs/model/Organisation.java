package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ОРГАНИЗАЦИИ")
public class Organisation implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @ManyToOne
    @JoinColumn(name = "МЕСТОПОЛОЖЕНИЕ", referencedColumnName = "ИД")
    private Place location;
    @Column(name = "ПОДОЗРИТЕЛЬНОСТЬ")
    private Double danger;
    protected Organisation(){}
    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public Double getDanger() {
        return danger;
    }

    public Place getLocation() {
        return location;
    }

    public void setDanger(Double danger) {
        this.danger = danger;
    }
}
