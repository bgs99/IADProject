package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ОТЧЕТ")
public class Report implements Serializable {
    @Id
    @GeneratedValue(generator = "otc")
    @SequenceGenerator(name = "otc", sequenceName = "otc", allocationSize = 1)
    @Column(name = "ИД")
    private int id;
    @ManyToOne
    @JoinColumn(name = "МИССИЯ", referencedColumnName = "ИД")
    private Mission mission;
    @Column(name = "НАИМЕНОВАНИЕ")
    private String name;
    @Column(name = "ПРИЧИНА")
    private String purpose;
    @Column(name = "ОПИСАНИЕ")
    private String description;
    @ManyToOne
    @JoinColumn(name = "СОЗДАЛ", referencedColumnName = "ИД")
    private Agent author;
    @ManyToOne
    @JoinColumn(name = "СУБЪЕКТ", referencedColumnName = "ИД")
    private Agent subject;
    protected Report(){}
    public Report(Mission mission, Agent subject, Agent author, String name, String description, String purpose){
        this.mission = mission;
        this.author = author;
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.purpose = purpose;
    }
    @Override
    public String toString(){
        return name + "\n" + description;
    }
    public int getId() {
        return id;
    }

    public Mission getMission() {
        return mission;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDescription() {
        return description;
    }

    public Agent getAuthor() {
        return author;
    }

    public Agent getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }
}
