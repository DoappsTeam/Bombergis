package me.doapps.bombergis;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26/05/2015.
 */
public class MainActivity extends ActionBarActivity {
    private GoogleMap map;
    private List<LatLng> hospitales;
    private List<LatLng> colegios;
    UiSettings mapSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();

        /*agregamos hospitales*/
        hospitales = new ArrayList<>();
        hospitales.add(new LatLng(-12.101524,-77.021626));
        hospitales.add(new LatLng(-12.097663,-77.022184));
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.getUiSettings().setZoomControlsEnabled(true);
        //mapSettings = map.getUiSettings();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);

        /*Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(-12.1023776, -77.0219219)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023776,-77.0219219))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
        Marker marker2 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1021255,-77.0219215))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776,-77.0219219))
                .zoom(16)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }

    Marker marker3;

    public void mostrarHospitales(View view){
      map.clear();

        for(int i = 0;i<hospitales.size();i++){
            marker3 = map.addMarker(new MarkerOptions()
                    .position(hospitales.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));
        }
    }

    public void mostrarColegios(View view){
        map.clear();

    }
}
