package bgs.info;

import bgs.model.Transport;
import org.springframework.stereotype.Component;

public class TransportInfo{
    public int id;
    public String name;
    public int size;
    public int count;
    private TransportInfo(){}
    public TransportInfo(Transport t){
        id = t.getId();
        name = t.toString();
        size = t.getSize();
        count = t.getReady();
    }
}
