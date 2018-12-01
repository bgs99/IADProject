package bgs.model;


import javax.persistence.*;

@Entity
@Table(name = "ГРАЖДАНЕ")
public class Person implements RegistryEntry {
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
    protected Person(){}
    @Override
    public String getName(){
        return name + " " + surname;
    }

    public char getSex() {
        return sex;
    }

    public String getBio() {
        return bio;
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

    @Override
    public void setDanger(Double danger) {
        this.danger = danger;
    }

    public Place getLocation() {
        return location;
    }

    public byte[] getData() {
        return data;
    }
}
