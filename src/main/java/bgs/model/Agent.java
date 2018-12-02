package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЧЛЕНЫ_ПОЛИЦИИ")
public class Agent implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @OneToOne
    @JoinColumn(name = "ПАСПОРТ", nullable = false, referencedColumnName = "ПАСПОРТ")
    private Person passport;
    @ManyToOne
    @JoinColumn(name = "ОТДЕЛ", referencedColumnName = "ИД")
    private Dept dept;
    @Column(name = "УРОВЕНЬ_ДОПУСКА")
    private int level;
    @Column(name = "ЗАРПЛАТА")
    private int payment;
    @Column(name = "ПАРОЛЬ")
    private String pass;
    @Column(name = "ТЕЛЕГРАМ")
    private Integer telegram;
    @Column(name = "ПОЧТА")
    private String email;

    public Integer getTelegram() {
        return telegram;
    }

    public String getEmail() {
        return email;
    }
    protected Agent(){}
    public Agent(int level, Dept dept, Person pass){
        this.level = level;
        this.dept = dept;
        this.passport = pass;
        this.payment = 0;
        this.pass = "aaaa";
    }

    public String getName(){
        return passport.getName();
    }

    public int getId() {
        return id;
    }

    public Dept getDept() {
        return dept;
    }

    public int getLevel() {
        return level;
    }

    public Person getPassport() {
        return passport;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPass(){
        return pass;
    }
}
