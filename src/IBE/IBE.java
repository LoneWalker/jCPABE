package ibe;

import it.unisa.dia.gas.jpbc.Element;
import utils.Constants;
import utils.Utils;

/**
 * Created by azhar on 12/16/16.
 */
public class IBE {


    public static IBECT encrypt(String ID, byte[] data, Element pk ){
        Element wj= Constants.PK.getPairing().getZr().newRandomElement();
        Element g_pow_wj=Constants.PK.g.duplicate().powZn(wj);
        Element hashedID= Utils.elementG1FromString(ID);
        Element hashedID_pow_wj=hashedID.duplicate().powZn(wj);
        Element pairing_ID = Constants.PK.getPairing().pairing(hashedID_pow_wj,pk);
        byte[]  hashed_pairing_ID= Utils.hashT(pairing_ID.toBytes());
        return new IBECT(g_pow_wj, Utils.XOR(data, hashed_pairing_ID),ID);
    }

    public static byte[] decrypt(IBECT ibect, Element sk){
        Element pairing_element= Constants.PK.getPairing().pairing(Utils.elementG1FromString(ibect.ID).powZn(sk),ibect.g_pow_w);
        byte[]  hashed_pairing_element= Utils.hashT(pairing_element.toBytes());
        return Utils.XOR(hashed_pairing_element,ibect.xored_data);
    }


}
