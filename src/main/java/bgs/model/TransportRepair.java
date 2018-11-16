package bgs.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "РЕМОНТ_ТРАНСПОРТА")
public class TransportRepair implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @ManyToOne
    @JoinColumn(name = "ТРАНСПОРТ", referencedColumnName = "ИД")
    private Transport transport;
    @ManyToOne
    @JoinColumn(name = "ОТВЕТСТВЕННЫЙ", referencedColumnName = "ИД")
    private Agent responisble;
    @Column(name = "ГОТОВНОСТЬ_К")
    private Timestamp ready;
    @Column(name = "НАЧАЛО")
    private Timestamp begin;
    protected TransportRepair(){}

    public TransportRepair(Transport weapon, Timestamp ready, Timestamp begin){
        this.transport = weapon;
        this.ready = ready;
        this.begin = begin;
    }

    public int getId() {
        return id;
    }

    public Transport getTransport() {
        return transport;
    }

    public Agent getResponisble() {
        return responisble;
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

    public void setResponisble(Agent responisble) {
        this.responisble = responisble;
    }
}
