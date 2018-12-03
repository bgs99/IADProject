package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ТРАНСПОРТ")
public class Transport implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @Column(name = "ГОТОВО_ЕДИНИЦ")
    private int ready;
    @Column(name = "ВМЕСТИМОСТЬ")
    private int size;
<<<<<<< HEAD
=======
    @Column(name = "ЦЕНА")
    private int price;
    @Column(name = "ЗАКАЗАНО")
    private int ordered;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    protected Transport(){}
    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

<<<<<<< HEAD
=======

    public int getPrice() {
        return price;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    public int getReady() {
        return ready;
    }
    public void incReady(){
        ready = ready + 1;
    }
<<<<<<< HEAD
=======

    public void incReady(int amount){
        ready = ready + amount;
    }
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    public void decReady(){
        ready = ready - 1;
    }
    public int getSize(){
        return size;
    }
}
