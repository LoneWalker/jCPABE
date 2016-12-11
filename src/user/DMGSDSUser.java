package user;

import it.unisa.dia.gas.jpbc.Element;
import tgdh.TGDHNode;
import utils.Constants;

/**
 * Created by azhar on 12/10/16.
 */
public class DMGSDSUser {
    TGDHNode TGDHleaf;
    public int GID;
    public int UID;
    public Element TGDH_K;
    public Element TGDH_BK;
    public DMGSDSUser(int UID, int GID, Element TGDH_K){
        this.UID=UID;
        this.GID=GID;
        this.TGDH_K = TGDH_K;
        this.TGDH_BK= Constants.PK.g.duplicate().powZn(this.TGDH_K);

    }

    public void setTGDHLeaf(TGDHNode TGDHleaf){
        this.TGDHleaf = TGDHleaf;
    }

    public TGDHNode getTGDHleaf(){
        return this.TGDHleaf;
    }
}
