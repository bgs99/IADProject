package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ОТДЕЛЫ")
public class Dept implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @ManyToOne
    @JoinColumn(name = "ПОДЧИНЯЕТСЯ", referencedColumnName = "ИД")
    private Dept parent;
    @OneToOne
    @JoinColumn(name = "НАЧАЛЬНИК", referencedColumnName = "ИД")
    private Agent boss;
    @ManyToOne
    @JoinColumn(name = "МЕСТОПОЛОЖЕНИЕ", referencedColumnName = "ИД")
    private Place location;
    protected Dept(){}
    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public Place getLocation() {
        return location;
    }

    public Agent getBoss() {
        return boss;
    }

    public Dept getParent() {
        return parent;
    }
}
