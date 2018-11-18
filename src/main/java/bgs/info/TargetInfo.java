package bgs.info;

import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.Target;

public class TargetInfo {
    public Person p;
    public Organisation o;
    public TargetInfo(Target t){
        p = t.getPerson();
        o = t.getOrganisation();
    }
}
