package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ОРУЖИЕ")
public class Weapon implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @Column(name = "ГОТОВО_ЕДИНИЦ")
    private int ready;
    @Column(name = "ЦЕНА")
    private int price;
    @Column(name = "ЗАКАЗАНО")
    private int ordered;
    protected Weapon(){}
    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getReady() {
        return ready;
    }
    public void incReady(){
        ready = ready + 1;
    }
    public void incReady(int amount){
        ready = ready + amount;
    }
    public void decReady(){
        ready = ready - 1;
    }
}
