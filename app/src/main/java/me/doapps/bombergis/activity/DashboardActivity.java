package me.doapps.bombergis.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import me.doapps.bombergis.R;
import me.doapps.bombergis.fragment.InterestFragment;
import me.doapps.bombergis.fragment.MapsFragment;
import me.doapps.bombergis.fragment.RouteFragment;
import me.doapps.bombergis.fragment.SearchFragment;

/**
 * Created by user on 27/05/2015.
 */
public class DashboardActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageButton btnSearch;
    private ImageButton btnInterest;
    private ImageButton btnMaps;
    private ImageButton btnRoute;
    private GoogleMap map;

    private List<LatLng> colegios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        /*inicializar el mapa*/
        setUpMapIfNeeded();
        /*cargar un fragment por defecto*/
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new SearchFragment()).commit();

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnInterest = (ImageButton) findViewById(R.id.btnInterest);
        btnInterest.setOnClickListener(this);

        btnMaps = (ImageButton) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(this);

        btnRoute = (ImageButton) findViewById(R.id.btnRoute);
        btnRoute.setOnClickListener(this);

 /*agregamos colegios*/
        colegios = new ArrayList<>();
        colegios.add(new LatLng(-12.106524, -77.025632));
        colegios.add(new LatLng(-12.095632, -77.025623));
        colegios.add(new LatLng(-12.156524, -77.054525));
        colegios.add(new LatLng(-12.086424, -77.096524));
        colegios.add(new LatLng(-12.164524, -77.046247));
        colegios.add(new LatLng(-12.067241, -77.057324));

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
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        //mapSettings = map.getUiSettings();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);

        /*Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(-12.1023776, -77.0219219)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776, -77.0219219))
                .zoom(14)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSearch:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, new SearchFragment())
                        .commit();
                break;

            case R.id.btnInterest:
                InterestFragment interestFragment = new InterestFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, interestFragment)
                        .commit();
                interestFragment.setInterfaceInstitutes(new InterestFragment.InterfaceInstitutes() {
                    @Override
                    public void getInstitute(int flag) {
                        if (flag == 1) {
                            for (int i = 0; i < colegios.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(colegios.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
                            }
                        }
                    }
                });
                break;

            case R.id.btnMaps:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, new MapsFragment())
                        .commit();
                break;

            case R.id.btnRoute:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, new RouteFragment())
                        .commit();
                break;

        }

    }
}
