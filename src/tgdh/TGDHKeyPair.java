package tgdh;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by azhar on 12/11/16.
 */
public class TGDHKeyPair {
    Element groupSK;
    Element groupPK;
    int TGDHGroupVersion;

    public TGDHKeyPair(int TGDHGroupVersion, Element groupSK, Element groupPK){
        this.TGDHGroupVersion=TGDHGroupVersion;
        this.groupSK=groupSK;
        this.groupPK=groupPK;
    }
}
