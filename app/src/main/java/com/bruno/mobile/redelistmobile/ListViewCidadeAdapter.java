package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bruno on 30/09/2014.
 */
public class ListViewCidadeAdapter extends BaseAdapter {

    // Declare Variables
    AlertDialog alert;
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();
    SharedPreferences sharedpreferences;

    public ListViewCidadeAdapter(Context context,
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
        TextView nmcidade;
        ImageView cidade_listview;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_cidade, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        nmcidade = (TextView) itemView.findViewById(R.id.nmcidade);

        // Locate the ImageView in listview_item.xml
        //cidade_listview = (ImageView) itemView.findViewById(R.id.cidade_listview);

        // Capture position and set results to the TextViews
        nmcidade.setText(resultp.get("nmcidade"));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        //imageLoader.DisplayImage(resultp.get("pais_listview"), cidade_listview);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Splash.IdCidade, resultp.get("idcidade").toString());
                editor.putString(Splash.Cidade, resultp.get("nmcidade").toString());
                editor.commit();


                Intent intent = new Intent(context,NavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        return itemView;
    }
    }
