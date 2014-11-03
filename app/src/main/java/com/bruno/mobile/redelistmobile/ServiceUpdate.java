package com.bruno.mobile.redelistmobile;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bruno on 02/10/2014.
 */
public class ServiceUpdate extends Service {
    int segundos = 1000;
    int minutos = 60 * segundos;
    int horas = 60 * minutos;
    int dia = 24 * horas;
    int delay = 1 * dia;

    private AutoUpdateApk aua;
    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.removeCallbacks(VerificaUpdate);
        handler.postDelayed(VerificaUpdate,delay);
    }
    private Runnable VerificaUpdate = new Runnable() {
        @Override
        public void run() {
            aua = new AutoUpdateApk(getApplicationContext());
            handler.postDelayed(this,delay);
        }
    };
}
