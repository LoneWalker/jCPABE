package tgdh;

import it.unisa.dia.gas.jpbc.Element;
import user.DMGSDSUser;

/**
 * Created by azhar on 12/10/16.
 */
public class TGDHNode {
    int l;
    int k;
    public TGDHNode parent=null;
    public TGDHNode lChild;
    public TGDHNode rChild;
    public int uid=-1;
    public Element K;
    public Element BK;
    public  boolean isLeaf=false;

    public TGDHNode(){
        l=0;
        k=0;
    }
    public TGDHNode(int l, int k){
        this.l=l;
        this.k=k;
    }

    public void setUser(DMGSDSUser user){
        this.K =user.TGDH_K;
        this.BK =user.TGDH_BK;
        uid=user.UID;
        user.setTGDHLeaf(this);
    }
}
