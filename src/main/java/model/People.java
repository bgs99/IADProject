package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ГРАЖДАНЕ")
public class People implements Serializable {
    @Id @GeneratedValue @Column(name = "ПАСПОРТ")
    private int id;
    @Column(name = "ИМЯ")
    private String name;
    @Column(name = "ФАМИЛИЯ")
    private String surname;
    @Column(name = "ПОЛ")
    private char sex;
    @ManyToOne
    @JoinColumn(name = "МЕСТОПОЛОЖЕНИЕ", referencedColumnName = "ИД")
    private Place location;
    @Column(name = "ПОДОЗРИТЕЛЬНОСТЬ")
    private Double danger;
    @Column(name = "БИОГРАФИЯ")
    private String bio;
    @Column(name = "БИОЛОГИЧЕСКИЕ_ДАННЫЕ")
    private byte[] data;
    protected People(){}
    @Override
    public String toString(){
        return name + " " + surname;
    }

    public int getId() {
        return id;
    }

    public boolean isMale(){
        char c = Character.toLowerCase(sex);
        return  c == 'm' || c == 'м';
    }

    public Double getDanger(){
        return danger;
    }

    public Place getLocation() {
        return location;
    }

    public byte[] getData() {
        return data;
    }
}
