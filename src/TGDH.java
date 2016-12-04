import cpabe.AbePublicKey;
import cpabe.AbeSettings;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
/**
 * Created by azhar on 12/3/16.
 */
public class TGDH {

    public void makeTGDHTree(){

        Field G1;
        AbePublicKey pub = new AbePublicKey(AbeSettings.curveParams);
        Element g = pub.getPairing().getG1().newRandomElement();

        Element k_1_0 = pub.getPairing().getZr().newRandomElement();
        Element k_1_1 = pub.getPairing().getZr().newRandomElement();

        Element bk_1_0=g.duplicate().powZn(k_1_0);
        Element bk_1_1=g.duplicate().powZn(k_1_1);

        Element k1_0_0=bk_1_0.duplicate().powZn(k_1_1);
        Element k2_0_0=bk_1_1.duplicate().powZn(k_1_0);

        Element bk1_0_0=g.duplicate().powZn(k1_0_0);
        Element bk2_0_0=g.duplicate().powZn(k2_0_0);

        System.out.println("K<0,0>1: "+k1_0_0);
        System.out.println("K<0,0>2: "+k2_0_0);

        System.out.println("BK<0,0>1: "+bk1_0_0);
        System.out.println("BK<0,0>2: "+bk2_0_0);
    }
}
