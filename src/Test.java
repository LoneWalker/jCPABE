import com.sun.org.apache.bcel.internal.generic.NEW;
import cpabe.*;
import cpabe.tests.TUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.Exchanger;

import static cpabe.Cpabe.decrypt;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by azhar on 11/2/16.
 */
public class Test {
    public static void main(String[] arg){

        /*
        String publicKeyFileName="publickey.txt";
        String masterKeyFileName="masterkey.txt";

        try {
            File publicKeyFile= new File(publicKeyFileName);
            File masterKeyFile= new File(masterKeyFileName);
            Cpabe.setup(publicKeyFile,masterKeyFile);
        }catch (Exception ex){
         ex.printStackTrace();
        }
        */

        try {
            AbeSecretMasterKey smKey = Cpabe.setup();
            AbePublicKey pubKey = smKey.getPublicKey();

            //byte[] data = TUtil.getRandomData();
            //byte[] data = {12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
            byte[] data = ("hhhjjhg").getBytes();
            String policy1 = "(att1 and att2) or att3";
            String policy2 = "att3 or att4 >= 5";

            AbeEncrypted policy1EncryptedTest = Cpabe.encrypt(pubKey, policy1, data);
            AbeEncrypted policy2EncryptedTest = Cpabe.encrypt(pubKey, policy2, data);

            //ByteArrayInputStream baisPolicy1 = TUtil.getReusableStream(policy1EncryptedTest, pubKey);
            //'ByteArrayInputStream baisPolicy2 = TUtil.getReusableStream(policy2EncryptedTest, pubKey);

            String att1att2Attribute = "att1 att2";
            String att3att4Attribute = "att3 att4 = 42";

            AbePrivateKey att1att2Key = Cpabe.keygen(smKey, att1att2Attribute);
            AbePrivateKey att3att4Key = Cpabe.keygen(smKey, att3att4Attribute);

            //assertTrue(Arrays.equals(data, decrypt(att1att2Key, AbeEncrypted.readFromStream(pubKey, baisPolicy1))));
            //assertFalse(Arrays.equals(data, decrypt(att3att4Key, AbeEncrypted.readFromStream(pubKey, baisPolicy2))));
            //TUtil.resetStreams(baisPolicy1, baisPolicy2);

            //System.out.println("Encrypted message:"+policy1EncryptedTest.toString());

            System.out.println("Original message of length:"+data.length);
            System.out.println(new String(data));
            printCipher(data);

            byte[] output = decrypt(att1att2Key, policy1EncryptedTest);
            System.out.println("Decrypted message1:");
            printCipher(output);

            output = decrypt(att3att4Key, policy2EncryptedTest);
            System.out.println("Decrypted message2:");
            printCipher(output);

            //System.out.println("Original message:"+new String(data));
            //System.out.println("Encrypted message:"+policy2EncryptedTest.toString());
            //System.out.println("Decrypted message:");
            //printCipher(output);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void printCipher(byte[] cipher){
        for (int i=0; i<cipher.length; i++)
            System.out.print(new Integer(cipher[i])+" ");
        System.out.println("");
    }
}
