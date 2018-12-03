package bgs.model;


import java.io.Serializable;
import java.sql.Timestamp;

public interface Repair extends Serializable {

    public int getId();

    public Agent getResponsible();

    public Timestamp getBegin();

    public Timestamp getReady();

    public void setReady(Timestamp ready);

    public void setResponsible(Agent responsible);

    public String getName();
}
