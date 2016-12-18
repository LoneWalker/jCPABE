package IBE;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by azhar on 12/16/16.
 */
public class IBECT {

    Element g_pow_w;
    byte[] xored_data;

    public IBECT(Element a, byte[] xored_data){
        this.g_pow_w=a;
        this.xored_data=xored_data;
    }
}
