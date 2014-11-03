package com.bruno.mobile.redelistmobile;

/**
 * Created by bruno on 30/10/2014.
 */
public class DrawerItem {
    String ItemName;
    int imgResID;
    String SubItemName;
    int layoutResID;

    public DrawerItem(String itemName, int imgResID, String SubItemName, int layoutResID) {
        super();
        this.ItemName = itemName;
        this.imgResID = imgResID;
        this.SubItemName = SubItemName;
        this.layoutResID = layoutResID;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
    public String getSubItemName() {
        return SubItemName;
    }
    public void setSubItemName(String SubitemName) {
        SubItemName = SubItemName;
    }
    public int getLayoutResID() {
        return layoutResID;
    }
    public void setLayoutResID(int layoutResID) {
        this.layoutResID = layoutResID;
    }
}
