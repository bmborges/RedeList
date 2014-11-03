package com.bruno.mobile.redelistmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.analytics.tracking.android.EasyTracker;


public class CidadeActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    ListView listview;
    private SearchView mSearchView;
    SharedPreferences sharedpreferences;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,PaisActivity.class);
        startActivity(intent);
        //super.onBackPressed();
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
        setContentView(R.layout.activity_cidade);
        setTitle("Cidade");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getSharedPreferences(Splash.MyPREFERENCES, Context.MODE_PRIVATE);

        String idpais = sharedpreferences.getString(Splash.IdPais,"");

        new DownloadCidadeJSON(CidadeActivity.this,idpais).execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cidade, menu);

        //MenuItem searchItem = menu.findItem(R.id.pesquisar);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //mSearchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this,PaisActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        new DownloadCidadeJSON(CidadeActivity.this,null).execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void voltarNavigation(){
        Intent intent = new Intent(this,NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
