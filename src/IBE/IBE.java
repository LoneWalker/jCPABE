package IBE;

import it.unisa.dia.gas.jpbc.Element;
import utils.Constants;
import utils.Utils;

/**
 * Created by azhar on 12/16/16.
 */
public class IBE {


    public static IBECT encrypt(String ID, byte[] data, Element PK_G ){
        Element wj= Constants.PK.getPairing().getZr().newRandomElement();
        Element g_pow_wj=Constants.PK.g.duplicate().powZn(wj);
        Element hashedID= Utils.elementG1FromString(ID);
        Element hashedID_pow_wj=hashedID.duplicate().powZn(wj);
        Element pairing_ID = Constants.PK.getPairing().pairing(hashedID_pow_wj,PK_G);
        byte[]  hashed_pairing_ID= Utils.hashT(pairing_ID.toBytes());
        return new IBECT(g_pow_wj, Utils.XOR(data, hashed_pairing_ID));
    }


}
