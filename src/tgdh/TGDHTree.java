package tgdh;

import it.unisa.dia.gas.jpbc.Element;
import user.DMGSDSUser;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by azhar on 12/10/16.
 */
public class TGDHTree {


    public static Element g;

    ArrayList<TGDHNode> leafList; // contains all the leaves in TGDH tree
    private int groupVersion=0;
    public TGDHNode root;
    public HashMap<DMGSDSUser,TGDHNode> userLeafNodeMap; // contains
    int totalUsers;


    public TGDHTree(int totalUsers){
        this.totalUsers=totalUsers;
        leafList= new ArrayList<>(totalUsers);
        userLeafNodeMap =new HashMap<>(totalUsers);
    }

    public TGDHTree(ArrayList<DMGSDSUser> userList){
        this.totalUsers=userList.size();
        leafList= new ArrayList<>(totalUsers);
        userLeafNodeMap =new HashMap<>(totalUsers);
        TGDHInit(userList);
    }

    public void TGDHInit(ArrayList<DMGSDSUser> userList){
        initTree();
        addAllUsers(userList);
        computeAllKeysRecursively(root);
    }

    private Element computeAllKeysRecursively(TGDHNode p){

        if (!p.isLeaf){
            TGDHNode ln = p.lChild;
            TGDHNode rn = p.rChild;

            ln.BK= Constants.PK.g.duplicate().powZn(computeAllKeysRecursively(ln));
            p.K = Utils.getZr(ln.BK.duplicate().powZn(computeAllKeysRecursively(rn)));
            p.BK=Constants.PK.g.duplicate().powZn(p.K);
        }
        return p.K;
    }


    private void initTree(){
        boolean isComplete=false;

        double x = Math.ceil(logBase2(totalUsers));
        double y = Math.floor(logBase2(totalUsers));
        if (x==y){
            isComplete=true;
        }
        int h = (int)x;

        if (isComplete){
            initCompleteBinTree(h);
        }else {
            initCompleteBinTree(h-1);
            int splitNodeCount = totalUsers - (int) Math.pow(2,h-1);
            for (int i = 0; i<splitNodeCount; i++){
                TGDHNode node = leafList.remove(0);
                splitNode(node);
                leafList.add(node.lChild);
                leafList.add(node.rChild);
            }

        }

    }

    private void splitNode(TGDHNode node){
        node.lChild= new TGDHNode((node.l+1),(2*node.k));
        node.rChild= new TGDHNode((node.l+1),(2*node.k+1));

        node.lChild.parent = node;
        node.rChild.parent = node;

        node.isLeaf=false;
        node.lChild.isLeaf=true;
        node.rChild.isLeaf=true;
    }
    private void mergeNode(TGDHNode node){
        node.isLeaf = true;

    }

    private void initCompleteBinTree(int h){
        root = new TGDHNode();
        buildCompleteBinTree(root,h);

    }
    private void buildCompleteBinTree(TGDHNode node, int h){

        if (node.l<h){
            node.lChild= new TGDHNode((node.l+1),(2*node.k));
            node.lChild.parent=node;
            buildCompleteBinTree(node.lChild,h);

            node.rChild= new TGDHNode((node.l+1),(2*node.k+1));
            node.rChild.parent= node;
            buildCompleteBinTree(node.rChild,h);

        }else if (node.l==h){
            node.isLeaf=true;
            leafList.add(node);
        }
    }

    private void addAllUsers(ArrayList<DMGSDSUser> userList){
        if (userList.size()>leafList.size()){
            System.out.println("Initially not enough empty leaves for users!! need to allocate new leaves by splitting");
        }else {
            for (int i=0; i<userList.size() ;i++){
                TGDHNode leaf=leafList.get(i);
                DMGSDSUser user = userList.get(i);
                leaf.setUser(user);
                userLeafNodeMap.put(user,leaf);
            }
        }
    }
    public void addUser(DMGSDSUser user){
            // find a appropriate position for user,
            //      if empty leaf found call setUser
            //      else call split and then setUser
    }

    public void revokeUser(){

    }

    public void pruneTree(){

    }
    private double logBase2(double val){
        return Math.log(val)/Math.log(2.0);
    }

    public int getGroupVersion(){
        return this.groupVersion;
    }
    public void incrementGroupVersion(){
        groupVersion++;
    }

}
