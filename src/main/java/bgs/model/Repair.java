package bgs.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

public interface Repair extends Serializable {

    public int getId();

    public Agent getResponisble();

    public Timestamp getBegin();

    public Timestamp getReady();

    public void setReady(Timestamp ready);

    public void setResponisble(Agent responsible);

    public String getName();
}
