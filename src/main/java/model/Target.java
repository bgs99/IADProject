package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЦЕЛИ")
public class Target implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @JoinColumn(name = "ПЕРСОНА", nullable = true, referencedColumnName = "ПАСПОРТ")
    @OneToOne
    private People person;
    @OneToOne
    @JoinColumn(name = "ОРГАНИЗАЦИЯ", nullable = true, referencedColumnName = "ИД")
    private Organisation organisation;
    protected Target(){}
    public Target(People person, Organisation organisation){
        this.person = person;
        this.organisation = organisation;
    }

    public int getId() {
        return id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public People getPerson() {
        return person;
    }

    public boolean isPerson(){
        return person != null;
    }
}
