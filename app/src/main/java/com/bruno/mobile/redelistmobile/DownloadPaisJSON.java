package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
public class DownloadPaisJSON extends AsyncTask<Void, Void, Void> {

    Activity activity;
    Context context;
    ListView listview;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListViewPaisAdapter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    ProgressDialog mProgressDialog;


    public DownloadPaisJSON(Context context){
        this.activity = (Activity) context;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(this.context);
        // Set progressdialog title
        mProgressDialog.setTitle("Lista Países");
        // Set progressdialog message
        mProgressDialog.setMessage("Consultando...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {


        // Create an array
        arraylist = new ArrayList<HashMap<String, String>>();
        String url = MainActivity.urlConsulta +"pais.php";
        EasyTracker.getInstance(this.context).send(MapBuilder.createEvent("evento", "consulta", url, null).build());
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
                map.put("nmpais", jsonobject.getString("nmpais"));
                map.put("idpais", jsonobject.getString("idpais"));
                // Set the JSON Objects into the array
                arraylist.add(map);
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void args) {
        // Locate the listview in listview_main.xml
        listview = (ListView) this.activity.findViewById(R.id.listView);
        // Pass the results into ListViewAdapter.java
        adapter = new ListViewPaisAdapter(context, arraylist);
        // Set the adapter to the ListView
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Close the progressdialog
        mProgressDialog.dismiss();
    }
}
