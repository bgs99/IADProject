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
    protected Transport(){}
    @Override
    public String toString(){
        return name;
    }

    public int getId() {
        return id;
    }

    public int getReady() {
        return ready;
    }
    public void incReady(){
        ready = ready + 1;
    }
    public void decReady(){
        ready = ready - 1;
    }
    public int getSize(){
        return size;
    }
}
