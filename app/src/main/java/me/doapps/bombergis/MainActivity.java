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
    private List<LatLng> hidrantes;
    private List<LatLng> bomberos;
    private List<LatLng> grifos;
    private List<LatLng> comisarias;
    UiSettings mapSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();

        /*agregamos hospitales*/
        hospitales = new ArrayList<>();
        hospitales.add(new LatLng(-12.101524, -77.021626));
        hospitales.add(new LatLng(-12.097663,-77.022184));
        hospitales.add(new LatLng(-12.154225, -77.055632));
        hospitales.add(new LatLng(-12.086472, -77.098543));
        hospitales.add(new LatLng(-12.162435, -77.045563));
        hospitales.add(new LatLng(-12.067863, -77.056342));
        /*agregamos colegios*/
        colegios=new ArrayList<>();
        colegios.add(new LatLng(-12.106524, -77.025632));
        colegios.add(new LatLng(-12.095632,-77.025623));
        colegios.add(new LatLng(-12.156524,-77.054525));
        colegios.add(new LatLng(-12.086424,-77.096524));
        colegios.add(new LatLng(-12.164524,-77.046247));
        colegios.add(new LatLng(-12.067241,-77.057324));
        /*agregamos hidrantes*/
        hidrantes=new ArrayList<>();
        hidrantes.add(new LatLng(-12.153566, -77.024524));
        hidrantes.add(new LatLng(-12.056345,-77.026525));
        hidrantes.add(new LatLng(-12.113454,-77.067862));
        hidrantes.add(new LatLng(-12.094755,-77.078424));
        hidrantes.add(new LatLng(-12.145256,-77.045675));
        hidrantes.add(new LatLng(-12.067345,-77.041455));
        /*agregamos bomberos*/
        bomberos=new ArrayList<>();
        bomberos.add(new LatLng(-12.113414, -77.056822));
        bomberos.add(new LatLng(-12.076245,-77.057686));
        bomberos.add(new LatLng(-12.157652,-77.089873));
        bomberos.add(new LatLng(-12.056735,-77.087252));
        bomberos.add(new LatLng(-12.156732,-77.067824));
        bomberos.add(new LatLng(-12.056782,-77.069435));
        /*agregramos grifos*/
        grifos=new ArrayList<>();
        grifos.add(new LatLng(-12.173525, -77.023456));
        grifos.add(new LatLng(-12.078356,-77.097354));
        grifos.add(new LatLng(-12.114794,-77.024562));
        grifos.add(new LatLng(-12.098435,-77.024662));
        grifos.add(new LatLng(-12.113514,-77.087356));
        grifos.add(new LatLng(-12.097452,-77.097356));
        /*agregramos comisarias*/
        comisarias=new ArrayList<>();
        comisarias.add(new LatLng(-12.114546, -77.076244));
        comisarias.add(new LatLng(-12.062461,-77.014462));
        comisarias.add(new LatLng(-12.178624,-77.076245));
        comisarias.add(new LatLng(-12.076243,-77.076254));
        comisarias.add(new LatLng(-12.076262,-77.052466));
        comisarias.add(new LatLng(-12.097624,-77.087343));

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
    Marker marker4;
    Marker marker5;
    Marker marker6;
    Marker marker7;
    Marker marker8;
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

        for(int i= 0;i<colegios.size();i++){
                marker4 = map.addMarker(new MarkerOptions()
                    .position(colegios.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));

        }
    }
    public void mostrarHidrantes(View view){
        map.clear();

        for(int i= 0;i<hidrantes.size();i++){
            marker5 = map.addMarker(new MarkerOptions()
                    .position(hidrantes.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hydrant)));

        }
    }
    public void mostrarBomberos(View view){
        map.clear();

        for(int i= 0;i<bomberos.size();i++){
            marker6 = map.addMarker(new MarkerOptions()
                    .position(bomberos.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));

        }
    }
    public void mostrarGrifos(View view){
        map.clear();

        for(int i= 0;i<grifos.size();i++){
            marker7 = map.addMarker(new MarkerOptions()
                    .position(grifos.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));

        }
    }
    public void mostrarComisarias(View view){
        map.clear();

        for(int i= 0;i<comisarias.size();i++){
            marker8 = map.addMarker(new MarkerOptions()
                    .position(comisarias.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_comisaria)));

        }
    }
}
