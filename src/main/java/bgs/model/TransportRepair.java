package bgs.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "РЕМОНТ_ТРАНСПОРТА")
public class TransportRepair implements Repair {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @ManyToOne
    @JoinColumn(name = "ТРАНСПОРТ", referencedColumnName = "ИД")
    private Transport transport;
    @ManyToOne
    @JoinColumn(name = "ОТВЕТСТВЕННЫЙ", referencedColumnName = "ИД")
    private Agent responsible;
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

    public String getName(){
        return transport.toString();
    }

    public int getId() {
        return id;
    }

    public Transport getTransport() {
        return transport;
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
