package cpabe.bsw07;

import it.unisa.dia.gas.jpbc.Element;

public class Bsw07CipherAndKey {
    private Bsw07Cipher cipher;
    private Element key;
    private Element groupDelimiter;

    public Bsw07CipherAndKey(Bsw07Cipher cipher, Element key, Element groupDelimiter) {
        this.cipher = cipher;
        this.key = key;
        this.groupDelimiter=groupDelimiter;
    }

    public Bsw07Cipher getCipher() {
        return cipher;
    }

    public Element getKey() {
        return key;
    }
    public Element getGroupDelimiter(){
        return this.groupDelimiter;
    }
}
