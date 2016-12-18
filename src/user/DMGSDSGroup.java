package user;

import it.unisa.dia.gas.jpbc.Element;
import tgdh.TGDHTree;

import java.util.ArrayList;

/**
 * Created by azhar on 12/10/16.
 */
public class DMGSDSGroup {

    int GID;
    int groupCurrVer;
    public ArrayList<DMGSDSUser> userList=new ArrayList<>();
    public TGDHTree tgdhTree;

    public DMGSDSGroup(int GID, ArrayList<DMGSDSUser> userList){
        this.userList = userList;
        this.GID = GID;
        this.groupCurrVer=0;
        // initializing TGDH tree
        tgdhTree= new TGDHTree(userList);
        tgdhTree.setGroupVersion(groupCurrVer);
        setDMGSDSGroupToAllUsers();

        // initializing TGDH tree done

    }

    public ArrayList<Element> getBKListofUserCoPath(DMGSDSUser user){

        if (userList.indexOf(user)>=0){
            return tgdhTree.getCoPathBKs(user);
        }else {
            System.out.println("The user does not belong to group "+GID);
            return null;
        }

    }
    private void setDMGSDSGroupToAllUsers(){
        for (int i=0; i<userList.size(); i++){
            userList.get(i).setGroup(this);
        }
    }
    public void addNewUser(DMGSDSUser user){

        // code add user in tgdh tree
        user.setGroup(this);

    }


}
