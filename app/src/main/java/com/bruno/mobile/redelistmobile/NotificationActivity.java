package com.bruno.mobile.redelistmobile;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by bruno on 17/09/2014.
 */
public class NotificationActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //nm.cancel(R.drawable.ic_launcher);
    }
}
