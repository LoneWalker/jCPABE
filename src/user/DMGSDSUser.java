package user;

import it.unisa.dia.gas.jpbc.Element;
import tgdh.TGDHNode;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;

/**
 * Created by azhar on 12/10/16.
 */
public class DMGSDSUser {
    TGDHNode TGDHleaf;
    DMGSDSGroup dmgsdsGroup;
    public int UID;
    public int GID;
    public Element TGDH_K;
    public Element TGDH_BK;

    public Element TGDH_GROUP_K;
    public Element TGDH_GROUP_BK;


    public DMGSDSUser(int UID, Element TGDH_K){
        this.UID=UID;
        this.TGDH_K = TGDH_K;
        this.TGDH_BK= Constants.PK.g.duplicate().powZn(this.TGDH_K);

    }

    public void setTGDHLeaf(TGDHNode TGDHleaf){
        this.TGDHleaf = TGDHleaf;
    }

    public TGDHNode getTGDHLeaf(){
        return this.TGDHleaf;
    }

    public void computeCurrentGroupKeyPair(){
        ArrayList<Element> BKList = dmgsdsGroup.getBKListofUserCoPath(this);
        if (BKList!=null && !BKList.isEmpty()){
            Element tempK=TGDH_K;
            Element tempBK=null;
            for (int i=0; i<BKList.size(); i++){
                tempK= Utils.getZr(BKList.get(i).duplicate().powZn(tempK));
                tempBK=Constants.PK.g.duplicate().powZn(tempK);
            }
            TGDH_K=tempK;
            TGDH_BK=tempBK;

        }else {
            System.out.println("BK list in user co-path is null");
        }


    }

    public void setGroup(DMGSDSGroup group){
        dmgsdsGroup=group;
        GID=dmgsdsGroup.GID;
    }
}
