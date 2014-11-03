package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bruno on 16/09/2014.
 */
public class Splash extends Activity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String IdPais = "IdpaisKey";
    public static final String Pais = "paisKey";
    public static final String IdCidade = "IdcidadeKey";
    public static final String Cidade = "cidadeKey";
    public static final ArrayList<HashMap<String, String>> arraylistTelefoneA =  new ArrayList<HashMap<String, String>>();
    public static final ArrayList<HashMap<String, String>> arraylistTelefone =  new ArrayList<HashMap<String, String>>();
    public static final ArrayList<HashMap<String, String>> arraylistTelefoneT =  new ArrayList<HashMap<String, String>>();


    SharedPreferences sharedpreferences;
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
        setContentView(R.layout.activity_splash);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Intent iServiceUpdate = new Intent(this, ServiceUpdate.class);
        //startService(iServiceUpdate);

        Thread splash_screen = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                    //startActivity(new Intent(getApplicationContext(), ListTelefonesActivity.class));
                    finish();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);

        if (!temConexao(Splash.this)) {
            builder.setTitle("Erro");
            builder.setMessage("Verifique a conexão com a internet");
            builder.setCancelable(true);
            builder.setNegativeButton("OK", new CancelOnClickListener());
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        } else {
            splash_screen.start();



        }
    }
    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Splash.this.finish();
        }
    }
    private boolean temConexao(Context classe) {
        //Pego a conectividade do contexto passado como argumento
        ConnectivityManager gerenciador = (ConnectivityManager) classe.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Crio a variável informacao que recebe as informações da Rede
        NetworkInfo informacao = gerenciador.getActiveNetworkInfo();
        //Se o objeto for nulo ou nao tem conectividade retorna false
        if ((informacao != null) && (informacao.isConnectedOrConnecting()) && (informacao.isAvailable())) {
            return true;
        }
        return false;
    }
   /* String getDeviceID(TelephonyManager phonyManager){

        String id = phonyManager.getDeviceId();
        if (id == null){
            id = "not available";
        }

        int phoneType = phonyManager.getPhoneType();
        switch(phoneType){
            case TelephonyManager.PHONE_TYPE_NONE:
                return "NONE: " + id;

            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM: IMEI=" + id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA: MEID/ESN=" + id;

            default:
                return "UNKNOWN: ID=" + id;
        }*/
}
