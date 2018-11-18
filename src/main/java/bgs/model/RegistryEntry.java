package bgs.model;


import javax.persistence.*;
import java.io.Serializable;

public interface RegistryEntry extends Serializable {

    public String getName();

    public int getId();

    public Double getDanger();

    public Place getLocation();

    public void setDanger(Double danger);
}
