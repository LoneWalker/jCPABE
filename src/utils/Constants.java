package utils;
import cpabe.AbePublicKey;
import cpabe.AbeSecretMasterKey;
import cpabe.Cpabe;

/**
 * Created by azhar on 12/10/16.
 *
 */

public class Constants {
    public static AbeSecretMasterKey MK = Cpabe.setup();
    public static AbePublicKey PK = MK.getPublicKey();
}
