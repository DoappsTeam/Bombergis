package me.doapps.bombergis;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

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

/**
 * Created by user on 26/05/2015.
 */
public class MainActivity extends ActionBarActivity {
    private GoogleMap map;
    UiSettings mapSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
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

        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023776, -77.0219219))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        Marker marker1 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023776,-77.0219219))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        Marker marker2 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023762, -77.0219762))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));
        Marker marker3 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023734,-77.0219975))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));
        Marker marker4 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023896,-77.0219146))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_comisaria)));
        Marker marker5 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023245,-77.0219985))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.hospital)));
        Marker marker6 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1023065,-77.0218354))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.hidrante)));
        Marker marker7 = map.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1021556,-77.0212456))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.colegio)));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776,-77.0219219))
                .zoom(16)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }




}
