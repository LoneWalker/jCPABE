package utils;
import cpabe.AbePublicKey;
import cpabe.AbeSecretMasterKey;
import cpabe.Cpabe;

/**
 * Created by azhar on 12/10/16.
 *
 */

public class Constants {

    public final static String SYM_ENC_ALGORITHM = "AES";
    public final static String PADDING_ALGORITHM = "AES/CBC/PKCS5Padding"; //"AES/GCM/NoPadding" not working on android
    public final static String HASHING_ALGORITHM = "SHA-256";
    public static final int HASH_OUTPUT_LENGTH_BYTES = 20;
    public static final int BUFFERSIZE = 1024;
    // We use AES128 per schneier, so we need to reduce the keysize


    public static AbeSecretMasterKey MK = Cpabe.setup();
    public static AbePublicKey PK = MK.getPublicKey();



}
