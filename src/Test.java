import com.sun.org.apache.bcel.internal.generic.NEW;
import cpabe.*;
import cpabe.tests.TUtil;
import it.unisa.dia.gas.jpbc.Element;
import tgdh.*;
import user.DMGSDSGroup;
import user.DMGSDSUser;
import utils.Constants;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
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

            /*************** CP-ABE initialization**********/

            Constants.MK = Cpabe.setup();
            Constants.PK = Constants.MK.getPublicKey();

            /************** end of CP-ABE initialization *******/

            ArrayList<DMGSDSUser> userArrayList = new ArrayList<>();

            /************* For Group 1****************/
            int totalUsers= 10;
            int GID1=0;



            long t= System.currentTimeMillis();
            for(int i=0; i<totalUsers; i++){
                userArrayList.add(new DMGSDSUser(i, Constants.PK.getPairing().getZr().newRandomElement()));
            }
            DMGSDSGroup dmgsdsGroup1= new DMGSDSGroup(GID1, userArrayList);
            dmgsdsGroup1.userList.get(3).computeCurrentGroupKeyPair();

            System.out.println("Success!!");

            // run for 3,4,5,6 attributes

            /*** I-ABDS***********/

            byte[] data = ("hhhjjhghkjhfaisudhfiaewuhflkjasdhflkjas").getBytes();
            String attributes[]={"att0","att1","att2","att3","att4","att5","att6","att7","att8","att9","att10"};
            String policy[] = {"","","","(att1 and att2) or att3","(att1 and att2) or (att3 and att4)",
                    "(att1 or att2 or att3) and att4 and att5 ","(att1 or att2 or att3) and att4 and (att5 or att6) "};
            int noOFUsers=800;
            int totalAttsInPolicy=6;
            int avgPolicyPerPerson=10;
            int membersPerGroup=50;
            int discUsers=(int)(noOFUsers/((double)avgPolicyPerPerson));
            int avgUserPerAttGroup=(int)(discUsers/2.0);


            String att1att2Attribute = "att1 att2 att3 att4 att5 att6";
            AbePrivateKey att1att2Key = Cpabe.keygen(Constants.MK, att1att2Attribute);

            t=System.currentTimeMillis();


            for (int i=0;i<discUsers; i++){
                Utils.IBEenc(attributes[9],dmgsdsGroup1.userList.get(3).TGDH_BK);
            }

            AbeEncrypted policy1EncryptedTest = Cpabe.encrypt(Constants.PK, policy[totalAttsInPolicy], data);
            Element r = Constants.PK.getPairing().getZr().newRandomElement();
            for (int j=0; j<totalAttsInPolicy; j++){
                Constants.PK.g.duplicate().powZn(r);
            }

            System.out.println("I-ABDS Encryption time"+(System.currentTimeMillis()-t));



            t=System.currentTimeMillis();

            /***********I-ABDS Decrypt*********/


            byte[] output = decrypt(att1att2Key, policy1EncryptedTest);


            for (int i=0;i<totalAttsInPolicy; i++){
                Utils.IBEenc(attributes[9],dmgsdsGroup1.userList.get(3).TGDH_BK);
            }


            int iteration=totalAttsInPolicy*(avgUserPerAttGroup+1);
            for (int j=0; j<iteration; j++){
                Constants.PK.g.duplicate().powZn(r);
            }



            System.out.println("I-ABDS Decrypt:"+(System.currentTimeMillis()-t));
            /*********** End I-ABDS Decrypt*********/



            /******** Our scheme ***********/
            /******* Our Enc************/

            t=System.currentTimeMillis();

            policy1EncryptedTest = Cpabe.encrypt(Constants.PK, policy[totalAttsInPolicy], data);

            iteration=(int) (totalUsers/((double)membersPerGroup));
            for (int i=0;i<iteration; i++){
                Utils.IBEenc(attributes[9],dmgsdsGroup1.userList.get(3).TGDH_BK);
            }






            System.out.println("Our encrypt:"+(System.currentTimeMillis()-t));
            /******* End our  Enc************/





            /*******Our  Dec ************/
            t=System.currentTimeMillis();


            decrypt(att1att2Key, policy1EncryptedTest);

            Utils.IBEenc(attributes[9],dmgsdsGroup1.userList.get(3).TGDH_BK);


            iteration=totalAttsInPolicy+Utils.logBase2(membersPerGroup);
            for (int j=0; j<iteration; j++){
                Constants.PK.g.duplicate().powZn(r);
            }



            System.out.println("Our Decrypt:"+(System.currentTimeMillis()-t));
            /******* End our Dec************/












            /*
            System.out.println("Time required for TGDH init ="+(System.currentTimeMillis()-t));

            byte[] a=Utils.hashT(Constants.PK.e_g_g_hat_alpha.toBytes());
            System.out.println("length of a ="+a.length+" ;value="+Utils.bytesToHex(a));




            //TGDH obj = new TGDH();
            //obj.makeTGDHTree();



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
            */

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
