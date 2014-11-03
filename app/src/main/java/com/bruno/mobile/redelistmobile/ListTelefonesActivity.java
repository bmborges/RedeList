package com.bruno.mobile.redelistmobile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;


public class ListTelefonesActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    ListView listview;
    ArrayList<HashMap<String, String>> arraylistMenuItem;
    JSONObject jsonobjectMenuItem;

    String anunciante = "S";
    String filtro = "Todos";
    //private LinearLayout footerLayout;
    boolean loadingMore = false;
    int offset = 0;
    int addOffset = 10;
    int limit  = 10;
    private int itensPorPagina = 10;
    private boolean carregandoMaisItens = false;
    private SearchView mSearchView;

    @Override
    public void onBackPressed() {
        Sair();
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
        setContentView(R.layout.activity_list_telefones);
        setTitle("RedeList");



        //criaRodapeDeMaisItens();

        listview = (ListView) findViewById(R.id.listView);
        new DownloadTelefoneJSON(ListTelefonesActivity.this,anunciante,offset,limit,filtro).execute();

        final Button bt_plus_empresa = (Button) findViewById(R.id.bt_plus_empresas);

        bt_plus_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anunciante.equals("S")){
                    offset = 0;
                    //filtro = "Todos";
                    anunciante = "N";
                    new DownloadTelefoneJSON(ListTelefonesActivity.this,anunciante,offset,limit,filtro).execute();
                    bt_plus_empresa.setText("Anunciantes");
                } else {
                    offset = 0;
                    //filtro = "Todos";
                    anunciante = "S";
                    new DownloadTelefoneJSON(ListTelefonesActivity.this,anunciante,offset,limit,filtro).execute();
                    bt_plus_empresa.setText("Mais Empresas");
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_telefones, menu);

        MenuItem searchItem = menu.findItem(R.id.pesquisar);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pesquisar){
            mSearchView.setIconified(true);
            return true;
        }
        /*if (id == R.id.pesquisar){
            final Button bt_plus_empresa = (Button) findViewById(R.id.bt_plus_empresas);

            final CharSequence[] items = {
                    "Alfabeto", "Categoria"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pesquisar Por");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item == 0 ){
                        final CharSequence[] letras = {
                                "Todos", "0-9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R",
                                "S","T","U","V","W","X","Y","Z"
                        };
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ListTelefonesActivity.this);
                        builder2.setTitle("Selecione uma Opção");
                        builder2.setItems(letras, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                filtro = (String) letras[i];
                                offset = 0;
                                anunciante = "S";
                                bt_plus_empresa.setText("Mais Empresas");
                                new DownloadJSON().execute();
                            }
                        });
                        AlertDialog alert2 = builder2.create();
                        alert2.show();
                    } else {
                        Toast.makeText(ListTelefonesActivity.this,"Opção Indisponível",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }*/
        if(id == R.id.sair){
            EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao","click","Sair",null).build());
            Sair();
            return true;
        }
        if(id == R.id.sobre){
            EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao","click","Sobre",null).build());
            startActivity(new Intent(getApplicationContext(), SobreActivity.class));
            return true;
        }
        /*if(id == R.id.configuracoes){
            startActivity(new Intent(getApplicationContext(), ConfiguracoesActivity.class));
            return true;
        }*/
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
    @Override
    public boolean onQueryTextSubmit(String s) {
        EasyTracker.getInstance(this).send(MapBuilder.createEvent("botao","click","Pesquisa",null).build());

        Intent intent = new Intent(getApplicationContext(),SearchListTelefonesActivity.class);
        intent.putExtra("filtro",s);
        startActivity(intent);


        return true;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private final class SairPositiveClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            ListTelefonesActivity.this.finish();
        }
    }
    private final class SairNegativeClickListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }
    @Override
    protected void onDestroy() {
        listview.setAdapter(null);
        super.onDestroy();
    }


}
