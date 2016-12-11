package user;

import tgdh.TGDHTree;

import java.util.ArrayList;

/**
 * Created by azhar on 12/10/16.
 */
public class DMGSDSGroup {

    int GID;
    ArrayList<DMGSDSUser> userList=new ArrayList<>();
    TGDHTree tgdhTree;

    public DMGSDSGroup(int GID, ArrayList<DMGSDSUser> userList){
        this.userList = userList;
        this.GID = GID;
        // initializing TGDH tree
        tgdhTree= new TGDHTree(userList);
        // initializing TGDH tree done


    }


}
