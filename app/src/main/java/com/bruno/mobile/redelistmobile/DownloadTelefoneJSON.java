package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bruno on 28/10/2014.
 */
public class DownloadTelefoneJSON extends AsyncTask<Void, Void, Void> {

    Activity activity;
    Context context;
    ListView listview;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    ProgressDialog mProgressDialog;
    String anunciante;
    String filtro = "Todos";
    int offset;
    int limit;


    public DownloadTelefoneJSON(Context context, String anunciante, int offset,int limit, String filtro){
        this.activity = (Activity) context;
        this.context = context;
        this.anunciante = anunciante;
        this.offset = offset;
        this.limit = limit;
        this.filtro = filtro;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(context);
        // Set progressdialog title
        mProgressDialog.setTitle("Lista Telefones");
        // Set progressdialog message
        mProgressDialog.setMessage("Consultando...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {


        // Create an array
        arraylist =  new ArrayList<HashMap<String, String>>();
        String url = MainActivity.urlConsulta +"consulta.php?anunciante="+anunciante+"&offset="+offset+"&limit="+limit+"&filtro="+filtro;
        Log.i("Script", url);
        EasyTracker.getInstance(context).send(MapBuilder.createEvent("evento", "consulta", url, null).build());
        // Retrieve JSON Objects from the given URL address
        jsonobject = JSONfunctions
                .getJSONfromURL(url);

        try {
            // Locate the array name in JSON
            jsonarray = jsonobject.getJSONArray("consulta");

            for (int i = 0; i < jsonarray.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                jsonobject = jsonarray.getJSONObject(i);
                // Retrive JSON Objects
                map.put("nmanunciante", jsonobject.getString("nmanunciante"));
                map.put("numero", jsonobject.getString("numero"));
                map.put("latitude", jsonobject.getString("latitude"));
                map.put("longitude", jsonobject.getString("longitude"));
                map.put("logo_listview", jsonobject.getString("logo_listview"));
                // Set the JSON Objects into the array
                if (anunciante.equals("S")){
                    Splash.arraylistTelefoneA.add(map);
                } else if (anunciante.equals("T")) {
                    Splash.arraylistTelefoneT.add(map);
                } else {
                    Splash.arraylistTelefone.add(map);
                }
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void args) {
        if (anunciante.equals("S")){
            arraylist = Splash.arraylistTelefoneA;
        } else if (anunciante.equals("T")) {
            arraylist = Splash.arraylistTelefoneT;
        } else {
            arraylist = Splash.arraylistTelefone;
        }

        // Locate the listview in listview_main.xml
        listview = (ListView) this.activity.findViewById(R.id.listView);
        // Pass the results into ListViewAdapter.java
        adapter = new ListViewAdapter(context, arraylist);
        // Set the adapter to the ListView
        listview.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        // Close the progressdialog
        mProgressDialog.dismiss();
    }
}
