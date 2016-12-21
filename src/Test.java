
import ibe.*;
import cpabe.*;
import it.unisa.dia.gas.jpbc.Element;
import user.DMGSDSGroup;
import user.DMGSDSUser;
import utils.Constants;

import java.util.ArrayList;

/**
 * Created by azhar on 11/2/16.
 */
public class Test {
    public static void main(java.lang.String[] arg){

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

            System.out.println("Successfully created group with total "+totalUsers+" users");



            // ENCRYPTION testing
                //CPABE
            byte[] data = ("hhhjjhg6971263947162394716234hjkashfkjhsgdkfhgskdhjfgkhj").getBytes();


            System.out.println("Original message in string: "+new String(data));
            printCipher(data);


            String policy1 = "(att1 and att2) or att3";
            //String policy2 = "att3 or att4 >= 5";

            String att1att2Attribute = "att1 att2";
            //String att3att4Attribute = "att3 att4 = 42";

            AbePrivateKey att1att2Key = Cpabe.keygen(Constants.MK, att1att2Attribute);
            //AbePrivateKey att3att4Key = Cpabe.keygen(Constants.MK, att3att4Attribute);
            AbeEncrypted policy1EncryptedTest = Cpabe.encrypt(Constants.PK, policy1, data);

                //IBE

            //System.out.println("Encrypting for data: "+policy1EncryptedTest.getGroupDelimiter());
            IBECT ibect = IBE.encrypt("0",policy1EncryptedTest.getGroupDelimiter().toBytes(), dmgsdsGroup1.tgdhTree.root.BK);


            // DECRYPTION

                //IBE

            byte[] decrypted_data=IBE.decrypt(ibect,dmgsdsGroup1.tgdhTree.root.K);
            Element groupDelimiter=Constants.PK.getPairing().getZr().newElementFromBytes(decrypted_data);
            //System.out.println("IBE Decrypted data :"+ groupDelimiter);

                //CPABE


            byte[] output = Cpabe.decrypt(att1att2Key, policy1EncryptedTest,groupDelimiter);
            System.out.println("Decrypted message in string: "+ new String(output));
            printCipher(output);

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
        System.out.println("Message in integer value:");
        for (int i=0; i<cipher.length; i++)
            System.out.print(new Integer(cipher[i])+" ");
        System.out.println("");
    }
}
