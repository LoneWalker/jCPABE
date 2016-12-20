package file;

import ibe.IBECT;
import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by azhar on 12/19/16.
 */
public class GroupToken {

    public int version;
    public Element PKG;
    public IBECT ibect;

    public GroupToken(int version, Element PKG, IBECT ibect){
        this.version=version;
        this.PKG = PKG;
        this.ibect = ibect;
    }
}
