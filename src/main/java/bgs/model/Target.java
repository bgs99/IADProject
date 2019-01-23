package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЦЕЛИ")
public class Target implements Serializable {
    @Id @GeneratedValue(generator = "tg")
    @SequenceGenerator(name = "tg", sequenceName = "tg", allocationSize = 1)
    @Column(name = "ИД")
    private int id;
    @JoinColumn(name = "ПЕРСОНА", referencedColumnName = "ПАСПОРТ")
    @OneToOne
    private Person person;
    @OneToOne
    @JoinColumn(name = "ОРГАНИЗАЦИЯ", referencedColumnName = "ИД")
    private Organisation organisation;
    protected Target(){}
    public Target(Person person){
        this.person = person;
        this.organisation = null;
    }
    public Target(Organisation organisation){
        this.person = null;
        this.organisation = organisation;
    }
    public int getId() {
        return id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isPerson(){
        return person != null;
    }
}
