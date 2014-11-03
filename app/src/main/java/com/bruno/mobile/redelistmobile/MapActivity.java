package com.bruno.mobile.redelistmobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.internal.la;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity {

    private GoogleMap map;
    private Marker marker;
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
        setContentView(R.layout.activity_map);

        String lat = this.getIntent().getExtras().getString("latitude");
        lat = lat.replace(",",".");
        String lng = this.getIntent().getExtras().getString("longitude");
        lng = lng.replace(",",".");

        String nmanunciante = this.getIntent().getExtras().getString("nmanunciante");

        FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.mapView);
        SupportMapFragment supportmapfragment = (SupportMapFragment) fragment;
        map = supportmapfragment.getMap();


        LatLng latlng = new LatLng(Float.parseFloat(lat),Float.parseFloat(lng));

        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        customMarker(latlng,"Marcador",nmanunciante);
    }
    public void customMarker(LatLng latlng, String title, String snippet){
        MarkerOptions options = new MarkerOptions();
        options.position(latlng).title(title).snippet(snippet).draggable(true);

        marker = map.addMarker(options);
        CameraPosition position = new CameraPosition.Builder().target(latlng).zoom(18).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);

        //map.moveCamera(update);
        map.animateCamera(update, 3000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onCancel() {

            }
        });

    }

}
