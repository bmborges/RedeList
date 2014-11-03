package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Created by bruno on 28/10/2014.
 */
public class SearchListTelefonesActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    String anunciante = "S";
    String filtro = "Todos";
    int offset = 0;
    int limit  = 10;

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("RedeList");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_search_list_telefones);
        offset = 0;
        filtro = getIntent().getExtras().get("filtro").toString().replaceAll(" ", "%20");
        anunciante = "T";
        new DownloadTelefoneJSON(SearchListTelefonesActivity.this,anunciante,offset,limit,filtro).execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_telefones, menu);

        MenuItem searchItem = menu.findItem(R.id.pesquisar);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setIconified(false);
        mSearchView.setQuery(getIntent().getExtras().get("filtro").toString(),false);
        mSearchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        offset = 0;
        filtro = s.replaceAll(" ","%20");
        anunciante = "T";
        new DownloadTelefoneJSON(SearchListTelefonesActivity.this,anunciante,offset,limit,filtro).execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Log.i("Script", "Clicou Home");
            this.finish();
            return true;
        }
        if(id == R.id.sair){
            EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao", "click", "Sair", null).build());
            Sair();
            return true;
        }
        if(id == R.id.sobre){
            EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao","click","Sobre",null).build());
            startActivity(new Intent(getApplicationContext(), SobreActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Sair(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair");
        builder.setMessage("Deseja realmente Sair?");
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new SairPositiveClickListener());
        builder.setNegativeButton("Nao", new SairNegativeClickListener());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private final class SairPositiveClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            SearchListTelefonesActivity.this.finish();
        }
    }
    private final class SairNegativeClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }
}
