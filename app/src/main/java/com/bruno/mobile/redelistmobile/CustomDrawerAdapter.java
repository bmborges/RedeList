package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bruno on 30/10/2014.
 */
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;

    public CustomDrawerAdapter(Context context,
                               List<DrawerItem> listItems) {
        super(context,0,listItems);
        this.context = context;
        this.drawerItemList = listItems;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            if (dItem.getLayoutResID() == R.layout.custom_drawer_header){

                view = inflater.inflate(dItem.getLayoutResID(), parent, false);
                drawerHolder.ItemName =  (TextView) view
                        .findViewById(R.id.drawer_itemName);

                drawerHolder.ItemName.setText(dItem.getItemName());

            } else if(dItem.getLayoutResID() == R.layout.custom_drawer_item){

                view = inflater.inflate(dItem.getLayoutResID(), parent, false);
                drawerHolder.ItemName = (TextView) view
                        .findViewById(R.id.drawer_itemName);
                drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

                drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                        dItem.getImgResID()));
                drawerHolder.ItemName.setText(dItem.getItemName());
            } else if(dItem.getLayoutResID() == R.layout.custom_drawer_item_2) {

                view = inflater.inflate(dItem.getLayoutResID(), parent, false);
                drawerHolder.ItemName = (TextView) view
                        .findViewById(R.id.drawer_itemName);
                drawerHolder.SubItemName = (TextView) view
                        .findViewById(R.id.drawer_subitemName);


                drawerHolder.ItemName.setText(dItem.getItemName());
                drawerHolder.SubItemName.setText(dItem.getSubItemName());
            }

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }





        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
        TextView SubItemName;
    }
}
