package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    AlertDialog alert;
    Context context;
    String NumeroTel;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
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
        TextView nmanunciante;
        TextView numero;
        ImageView logo_listview;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        nmanunciante = (TextView) itemView.findViewById(R.id.nmanunciante);
        numero = (TextView) itemView.findViewById(R.id.numero);

        // Locate the ImageView in listview_item.xml
        logo_listview = (ImageView) itemView.findViewById(R.id.logo_listview);

        // Capture position and set results to the TextViews
        nmanunciante.setText(resultp.get("nmanunciante"));
        numero.setText(resultp.get("numero"));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class

        imageLoader.DisplayImage(resultp.get("logo_listview"), logo_listview);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);

                cria_lista_single(resultp);
            }
        });
        return itemView;
    }
    private final class CancelarNegativeClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            alert.hide();
        }
    }
    private void cria_lista_single(final HashMap result) {
        if (result.get("numero").toString().length() <= 0){
            return;
        }
        CharSequence[] items = null;
        String latitude = result.get("latitude").toString();
        latitude = latitude.replace(",",".");
        String longitude =  result.get("longitude").toString();
        longitude = longitude.replace(",",".");
        if (Float.parseFloat(latitude) > 0 && Float.parseFloat(longitude) > 0){
            items = new CharSequence[]{"Ligar", "Ver Localização"};
        } else {
            items = new CharSequence[]{"Ligar"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Você deseja ?");
        builder.setNegativeButton("Cancelar", new CancelarNegativeClickListener());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (item == 0 ) {
                    if (result.get("numero").toString().length() > 0) {
                        NumeroTel = result.get("numero").toString();
                        EasyTracker.getInstance(context).send(MapBuilder.createEvent("evento", "ligar", result.get("nmanunciante").toString(), null).build());

                        ligar();
                    } else {
                        Toast.makeText(context, "Número não Localizado ", Toast.LENGTH_SHORT).show();
                    }
                } else if (item == 1){
                            EasyTracker.getInstance(context).send(MapBuilder.createEvent("evento", "localizacao", result.get("nmanunciante").toString(), null).build());
                        //String latitude = result.get("latitude").toString();
                        //String longitude =  result.get("longitude").toString();
                        //if (Float.parseFloat(latitude) > 0 && Float.parseFloat(longitude) > 0){
                            Intent intent = new Intent(context, MapActivity.class);
                            intent.putExtra("latitude", result.get("latitude").toString());
                            intent.putExtra("longitude", result.get("longitude").toString());
                            intent.putExtra("nmanunciante", result.get("nmanunciante").toString());
                            context.startActivity(intent);
                        //} else {
                        //    Toast.makeText(context, "Localizaçao não Encontrada ", Toast.LENGTH_SHORT).show();
                        //}
                } else {
                    Toast.makeText(context,"Opção Indisponível",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert = builder.create();
        alert.show();


    }
    private void ligar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Aviso");
        builder.setMessage("Valores podem ser cobrados pela operadora. A ligação não e pela Internet");
        builder.setCancelable(true);
        builder.setPositiveButton("Ligar", new LigarPositiveClickListener());
        builder.setNegativeButton("Cancelar", new LigarNegativeClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private final class LigarPositiveClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(context, "Ligando... ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NumeroTel));
            context.startActivity(i);
        }
    }
    private final class LigarNegativeClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }
    }
