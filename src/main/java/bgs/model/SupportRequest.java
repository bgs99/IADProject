package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЗАПРОСЫ_ПОДДЕРЖКИ")
public class SupportRequest implements Serializable {
    @Id
    @GeneratedValue(generator = "ЗАПРОСЫ_ПОДДЕРЖКИ_ИД_seq")
    @SequenceGenerator(name = "ЗАПРОСЫ_ПОДДЕРЖКИ_ИД_seq", sequenceName = "ЗАПРОСЫ_ПОДДЕРЖКИ_ИД_seq", allocationSize = 1)
    @Column(name = "ИД")

    private int id;
    @ManyToOne
    @JoinColumn(name = "МИССИЯ", referencedColumnName = "ИД")
    private Mission mission;
    @Column(name = "БОЙЦЫ")
    private Integer soldiers;
    @Column(name = "ДАННЫЕ")
    private String data;
    @ManyToOne
    @JoinColumn(name = "ТРАНСПОРТ", referencedColumnName = "ИД")
    private Transport transport;
    @ManyToOne
    @JoinColumn(name = "ОРУЖИЕ", referencedColumnName = "ИД")
    private Weapon weapon;
    @Column(name = "РАССМОТРЕН")
    private boolean seen;
    protected SupportRequest(){}
    public SupportRequest(Mission mission, String data, Integer soldiers, Transport transport, Weapon weapon){
        this.mission = mission;
        this.data = data;
        this.soldiers = soldiers;
        this.transport = transport;
        this.weapon = weapon;
        seen = false;
    }
    public int getId() {
        return id;
    }

    public Mission getMission() {
        return mission;
    }

    public Integer getSoldiers() {
        return soldiers;
    }

    public String getData() {
        return data;
    }

    public Transport getTransport() {
        return transport;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen() {
        this.seen = true;
    }
}
