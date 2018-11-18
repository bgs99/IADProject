package bgs.info;

import bgs.model.Weapon;
import org.springframework.stereotype.Component;

public class WeaponInfo{
    public int id;
    public String name;
    public int count;
    private WeaponInfo(){}
    public WeaponInfo(Weapon t){
        id = t.getId();
        name = t.toString();
        count = t.getReady();
    }
}
