import cpabe.AbePublicKey;
import cpabe.AbeSettings;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.math.BigInteger;

/**
 * Created by azhar on 12/3/16.
 */
public class TGDH {

    public void makeTGDHTree(){

        AbePublicKey pub = new AbePublicKey(AbeSettings.curveParams);
        Element g = pub.getPairing().getG1().newRandomElement();

        Element k_1_0 = pub.getPairing().getZr().newRandomElement();
        Element k_1_1 = pub.getPairing().getZr().newRandomElement();



        Element bk_1_0=g.duplicate().powZn(k_1_0);
        Element bk_1_1=g.duplicate().powZn(k_1_1);




        Element k1_0_0=bk_1_0.duplicate().powZn(k_1_1);
        Element k2_0_0=bk_1_1.duplicate().powZn(k_1_0);

        Element bk1_0_0=g.duplicate().powZn(getZr(k1_0_0,pub));
        Element bk2_0_0=g.duplicate().powZn(getZr(k2_0_0,pub));

        System.out.println("K<0,0>1: "+k1_0_0);
        System.out.println("K<0,0>2: "+k2_0_0);

        System.out.println("BK<0,0>1: "+bk1_0_0);
        System.out.println("BK<0,0>2: "+bk2_0_0);

        System.out.println("random Zr:"+k_1_0);
        
    }

    private Element getZr(Element e, AbePublicKey pub){

        String val = e.toString().split(",")[0];
        return pub.getPairing().getZr().newElement(new BigInteger(val));
    }
}
