package utils;

import cpabe.AbePublicKey;
import cpabe.AbeSettings;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;

import static utils.Constants.HASHING_ALGORITHM;

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

    public static byte[] hashT(byte[] message) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance(HASHING_ALGORITHM);
            return Arrays.copyOf(sha256.digest(message), Constants.HASH_OUTPUT_LENGTH_BYTES);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing Alogrithm not available: " + HASHING_ALGORITHM, e);
        }
    }

    public static byte[] XOR(byte[] a, byte[] b){
        byte[] result= new byte[a.length];
        if (a.length==b.length){

            for (int i=0; i<a.length; i++){
                result[i]= (byte) (a[i]^b[i]);
            }

            return result;
        }else {
            System.out.println("both input length should be same in xor oeration");
            return null;
        }

    }


    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] IBEenc(String GID, Element PK_G){
        Element wj=Constants.PK.getPairing().getZr().newRandomElement();
        Element g_wj=Constants.PK.g.duplicate().powZn(wj);
        Element hashedGID= elementG1FromString(GID);
        Element hashedGID_pow_wj=hashedGID.duplicate().powZn(wj);
        Element e = Constants.PK.getPairing().pairing(hashedGID_pow_wj,PK_G);
        return hashT(e.toBytes());
    }

    public static Element elementG1FromString(String s) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance(AbeSettings.ELEMENT_HASHING_ALGORITHM);
            byte[] digest = sha1.digest(s.getBytes());
            return Constants.PK.getPairing().getG1().newElementFromHash(digest, 0, digest.length);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing Alogrithm not available: " + AbeSettings.ELEMENT_HASHING_ALGORITHM, e);
        }
    }

    public static int logBase2(double val){
        return (int)(Math.log(val)/Math.log(2.0));
    }

}
