package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЧЛЕНЫ_ПОЛИЦИИ")
public class Agent implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @OneToOne
    @JoinColumn(name = "ПАСПОРТ", nullable = false, referencedColumnName = "ПАСПОРТ")
    private People passport;
    @ManyToOne
    @JoinColumn(name = "ОТДЕЛ", referencedColumnName = "ИД")
    private Dept dept;
    @Column(name = "УРОВЕНЬ_ДОПУСКА")
    private int level;
    @Column(name = "ЗАРПЛАТА")
    private int payment;
    @Column(name = "ПАРОЛЬ")
    private String pass;
    @Column(name = "СОЛЬ")
    private String salt;

    protected Agent(){}
    public Agent(int level, Dept dept, People pass){
        this.level = level;
        this.dept = dept;
        this.passport = pass;
        this.payment = 0;
        this.pass = "aaaa";
        this.salt = "bbbb";
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

    public People getPassport() {
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

    public String getSalt() {
        return salt;
    }
}
