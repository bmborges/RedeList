package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bruno on 30/09/2014.
 */
public class ListViewPaisAdapter extends BaseAdapter {

    // Declare Variables
    AlertDialog alert;
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();
    SharedPreferences sharedpreferences;

    public ListViewPaisAdapter(Context context,
                               ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
        sharedpreferences = context.getSharedPreferences(Splash.MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView nmpais;
        ImageView pais_listview;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_pais, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        nmpais = (TextView) itemView.findViewById(R.id.nmpais);

        // Locate the ImageView in listview_item.xml
        //pais_listview = (ImageView) itemView.findViewById(R.id.pais_listview);

        // Capture position and set results to the TextViews
        nmpais.setText(resultp.get("nmpais"));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        //imageLoader.DisplayImage(resultp.get("pais_listview"), pais_listview);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Splash.IdPais, resultp.get("idpais").toString());
                editor.putString(Splash.Pais, resultp.get("nmpais").toString());
                editor.commit();


                //cidade
                Intent cidade = new Intent(context, CidadeActivity.class);
                context.startActivity(cidade);
            }
        });
        return itemView;
    }
    }
