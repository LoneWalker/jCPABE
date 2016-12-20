package file;

import cpabe.AbeEncrypted;
import ibe.IBE;
import ibe.IBECT;
import it.unisa.dia.gas.jpbc.Element;
import utils.Utils;

import java.util.HashMap;

/**
 * Created by azhar on 12/19/16.
 */
public class CTFULL {

    boolean isDirty;
    public HashMap<Integer,GroupToken> ctg;
    public int FileID;
    public AbeEncrypted CTcpabe;
    public byte[] CTF;


    public CTFULL(){
        ctg= new HashMap<>();
    }



    public void addGroupToken(int IDGroup, Element groupDelimiter, Element PKG){
        ctg.put(IDGroup, createGroupToken(IDGroup, groupDelimiter, PKG));
    }

    private GroupToken createGroupToken(int ID, Element groupDelimiter, Element PKG){
        return new GroupToken(ID,PKG, IBE.encrypt(String.valueOf(ID), groupDelimiter.toBytes(),PKG));
    }


}


