package bgs.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "РЕМОНТ_ОРУЖИЯ")
public class WeaponRepair implements Repair {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @ManyToOne
    @JoinColumn(name = "ОРУЖИЕ", referencedColumnName = "ИД")
    private Weapon weapon;
    @ManyToOne
    @JoinColumn(name = "ОТВЕТСТВЕННЫЙ", referencedColumnName = "ИД")
    private Agent responsible;
    @Column(name = "ГОТОВНОСТЬ_К")
    private Timestamp ready;
    @Column(name = "НАЧАЛО")
    private Timestamp begin;
    protected WeaponRepair(){}

    public WeaponRepair(Weapon weapon, Timestamp ready, Timestamp begin){
        this.weapon = weapon;
        this.ready = ready;
        this.begin = begin;
    }

    public String getName(){
        return weapon.toString();
    }

    public int getId() {
        return id;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Agent getResponsible() {
        return responsible;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public Timestamp getReady() {
        return ready;
    }

    public void setReady(Timestamp ready) {
        this.ready = ready;
    }

    public void setResponsible(Agent responsible) {
        this.responsible = responsible;
    }
}
