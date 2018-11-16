package model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ЗАПРОСЫ_ИНФОРМАЦИИ")
public class InfoRequest implements Serializable {
    @Id @GeneratedValue @Column(name = "ИД")
    private int id;
    @ManyToOne
    @JoinColumn(name = "АГЕНТ", referencedColumnName = "ИД")
    private Agent agent;
    @Column(name = "СЕКРЕТНОСТЬ")
    private int level;
    @Column(name = "СОДЕРЖАНИЕ_ЗАПРОСА")
    private String request;
    @Column(name = "ОБЪЯСНИТЕЛЬНАЯ")
    private String purpose;
    @Column(name = "СТАТУС")
    private String status;
    @Column(name = "ОТВЕТ")
    private String response;
    protected InfoRequest(){}
    public InfoRequest(Agent agent, String request, String purpose){
        this.agent = agent;
        this.request = request;
        this.status = "Ожидает обработки";
        this.purpose = purpose;
        this.level = 0;
    }
    @Override
    public String toString(){
        return request + "\n" + purpose;
    }

    public int getId() {
        return id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
