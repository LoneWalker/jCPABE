package cpabe.bsw07;

import cpabe.AbeInputStream;
import cpabe.AbeOutputStream;
import cpabe.bsw07.policy.Bsw07PolicyAbstractNode;
import it.unisa.dia.gas.jpbc.Element;

import java.io.IOException;

public class Bsw07Cipher {
    /*
     * A ciphertext. Note that this library only handles encrypting a single group element, so if you want to encrypt something
     * bigger, you will have to use that group element as a symmetric key for hybrid encryption (which you do yourself).
     */
    public Bsw07PolicyAbstractNode policyTree;
    /**
     * GT
     **/
    private byte[] cs;
    /**
     * G1
     **/
    private Element c;

    public Bsw07Cipher(Bsw07PolicyAbstractNode policy, byte[] cs, Element c) {
        this.policyTree = policy;
        this.cs = cs;
        this.c = c;
    }

    public static Bsw07Cipher readFromStream(AbeInputStream stream) throws IOException {
        byte[] cs = stream.readBytes();
        Element c = stream.readElement();
        Bsw07PolicyAbstractNode policyTree = Bsw07PolicyAbstractNode.readFromStream(stream);
        return new Bsw07Cipher(policyTree, cs, c);
    }

    public byte[] getCs() {
        return cs;
    }

    public Element getC() {
        return c;
    }

    public void writeToStream(AbeOutputStream stream) throws IOException {
        stream.writeBytes(cs);
        stream.writeElement(c);
        policyTree.writeToStream(stream);
    }
}
