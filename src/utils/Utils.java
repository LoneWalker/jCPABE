package utils;

import cpabe.AbePublicKey;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * Created by azhar on 12/11/16.
 */
public class Utils {


    /*
        @param Element in G1 or G2
     */

    public static Element getZr(Element e){
        String val = e.toString().split(",")[0];
        return Constants.PK.getPairing().getZr().newElement(new BigInteger(val));

    }
}
