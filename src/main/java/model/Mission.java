package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "МИССИИ")
public class Mission implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    //@OneToOne
    //@JoinColumn(name = "ПАСПОРТ", nullable = false, referencedColumnName = "ПАСПОРТ")
    @Column(name = "ТИП", nullable = false)
    private int type;
    @Column(name = "ОПИСАНИЕ")
    private String description;
    @Column(name = "СЕКРЕТНОСТЬ")
    private int level;
    @Column(name = "СТАТУС")
    private String status;
    @ManyToOne
    @JoinColumn(name = "ЦЕЛЬ", referencedColumnName = "ИД")
    private Target target;
    @ManyToOne
    @JoinColumn(name = "ОТВЕТСТВЕННЫЙ", referencedColumnName = "ИД")
    private Agent responsible;

    protected Mission(){}

    public int getId() {
        return id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agent getResponsible() {
        return responsible;
    }

    public Target getTarget() {
        return target;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponsible(Agent responsible) {
        this.responsible = responsible;
    }
}
