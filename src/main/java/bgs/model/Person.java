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
<<<<<<< HEAD
=======
    @Column(name = "ФОТО")
    private String image;
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    protected Person(){}
    @Override
    public String getName(){
        return name + " " + surname;
    }

<<<<<<< HEAD
=======
    public String getImage() {
        return image;
    }

>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
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
