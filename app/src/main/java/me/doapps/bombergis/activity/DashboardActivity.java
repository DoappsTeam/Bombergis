package me.doapps.bombergis.activity;

import android.annotation.TargetApi;
import android.os.Build;
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
import me.doapps.bombergis.operation.MapOperation;

/**
 * Created by user on 27/05/2015.
 */
public class DashboardActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageButton btnSearch;
    private ImageButton btnInterest;
    private ImageButton btnMaps;
    private ImageButton btnRoute;
    private GoogleMap map;

    private List<LatLng> hospitales;
    private List<LatLng> colegios;
    private List<LatLng> hidrantes;
    private List<LatLng> bomberos;
    private List<LatLng> grifos;
    private List<LatLng> comisarias;

    static int stateMapsDashboard = 1;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        /*inicializar el mapa*/
        setUpMapIfNeeded();


        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnInterest = (ImageButton) findViewById(R.id.btnInterest);
        btnInterest.setOnClickListener(this);

        btnMaps = (ImageButton) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(this);

        btnRoute = (ImageButton) findViewById(R.id.btnRoute);
        btnRoute.setOnClickListener(this);

            /*cargar un fragment por defecto*/
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, searchFragment).commit();
        searchFragment.setInterfaceSearch(new SearchFragment.InterfaceSearch() {
            @Override
            public void getAddress(String address) {
                MapOperation mapOperation = new MapOperation();
                mapOperation.getReferences(address);
                Toast.makeText(DashboardActivity.this, address, Toast.LENGTH_SHORT).show();
            }
        });


/*agregamos hospitales*/
        hospitales = new ArrayList<>();
        hospitales.add(new LatLng(-12.101524, -77.021626));
        hospitales.add(new LatLng(-12.097663, -77.022184));
        hospitales.add(new LatLng(-12.154225, -77.055632));
        hospitales.add(new LatLng(-12.086472, -77.098543));
        hospitales.add(new LatLng(-12.162435, -77.045563));
        hospitales.add(new LatLng(-12.067863, -77.056342));
        /*agregamos colegios*/
        colegios = new ArrayList<>();
        colegios.add(new LatLng(-12.106524, -77.025632));
        colegios.add(new LatLng(-12.095632, -77.025623));
        colegios.add(new LatLng(-12.156524, -77.054525));
        colegios.add(new LatLng(-12.086424, -77.096524));
        colegios.add(new LatLng(-12.164524, -77.046247));
        colegios.add(new LatLng(-12.067241, -77.057324));
        /*agregamos hidrantes*/
        hidrantes = new ArrayList<>();
        hidrantes.add(new LatLng(-12.153566, -77.024524));
        hidrantes.add(new LatLng(-12.056345, -77.026525));
        hidrantes.add(new LatLng(-12.113454, -77.067862));
        hidrantes.add(new LatLng(-12.094755, -77.078424));
        hidrantes.add(new LatLng(-12.145256, -77.045675));
        hidrantes.add(new LatLng(-12.067345, -77.041455));
        /*agregamos bomberos*/
        bomberos = new ArrayList<>();
        bomberos.add(new LatLng(-12.113414, -77.056822));
        bomberos.add(new LatLng(-12.076245, -77.057686));
        bomberos.add(new LatLng(-12.157652, -77.089873));
        bomberos.add(new LatLng(-12.056735, -77.087252));
        bomberos.add(new LatLng(-12.156732, -77.067824));
        bomberos.add(new LatLng(-12.056782, -77.069435));
        /*agregramos grifos*/
        grifos = new ArrayList<>();
        grifos.add(new LatLng(-12.173525, -77.023456));
        grifos.add(new LatLng(-12.078356, -77.097354));
        grifos.add(new LatLng(-12.114794, -77.024562));
        grifos.add(new LatLng(-12.098435, -77.024662));
        grifos.add(new LatLng(-12.113514, -77.087356));
        grifos.add(new LatLng(-12.097452, -77.097356));
        /*agregramos comisarias*/
        comisarias = new ArrayList<>();
        comisarias.add(new LatLng(-12.114546, -77.076244));
        comisarias.add(new LatLng(-12.062461, -77.014462));
        comisarias.add(new LatLng(-12.178624, -77.076245));
        comisarias.add(new LatLng(-12.076243, -77.076254));
        comisarias.add(new LatLng(-12.076262, -77.052466));
        comisarias.add(new LatLng(-12.097624, -77.087343));

    }


    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpSearchFragment(){

    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);

        if (stateMapsDashboard == 1) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (stateMapsDashboard == 2) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (stateMapsDashboard == 3) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        //mapSettings = map.getUiSettings();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);

        /*Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(-12.1023776, -77.0219219)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776, -77.0219219))
                .zoom(16)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSearch:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search2));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, searchFragment)
                        .commit();
                searchFragment.setInterfaceSearch(new SearchFragment.InterfaceSearch() {
                    @Override
                    public void getAddress(String address) {
                        //MapOperation mapOperation = new MapOperation();
                        //mapOperation.getReferences(address);
                        Toast.makeText(DashboardActivity.this, address, Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.btnInterest:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest2));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                InterestFragment interestFragment = new InterestFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, interestFragment)
                        .commit();

                interestFragment.setInterfaceInstitutes(new InterestFragment.InterfaceInstitutes() {
                    @Override
                    public void getInstitute(int flag1, int flag2, int flag3, int flag4, int flag5, int flag6) {
                        //getInstitute(stateHospitales,stateColegios,stateHidrantes,stateBomberos,stateGrifos,stateComisarias);
                        map.clear();

                        if (flag1 == 1) {
                            for (int i = 0; i < hospitales.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(hospitales.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));
                            }
                        }

                        if (flag2 == 1) {
                            for (int i = 0; i < colegios.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(colegios.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
                            }
                        }

                        if (flag3 == 1) {
                            for (int i = 0; i < hidrantes.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(hidrantes.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hydrant)));
                            }
                        }

                        if (flag4 == 1) {
                            for (int i = 0; i < bomberos.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(bomberos.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));
                            }
                        }

                        if (flag5 == 1) {
                            for (int i = 0; i < grifos.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(grifos.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));
                            }
                        }

                        if (flag6 == 1) {
                            for (int i = 0; i < comisarias.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(comisarias.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_policestation)));
                            }
                        }
                    }
                });
                break;

            case R.id.btnMaps:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps2));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                MapsFragment mapsFragment = new MapsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, mapsFragment)
                        .commit();

                mapsFragment.setInterfaceChangeMaps(new MapsFragment.InterfaceChangeMaps() {
                    @Override
                    public void getMaps(int flag) {
                        if (flag == 1) {
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            stateMapsDashboard = 1;
                        }

                        if (flag == 2) {
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            stateMapsDashboard = 2;
                        }

                        if (flag == 3) {
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            stateMapsDashboard = 3;
                        }
                    }
                });

                break;

            case R.id.btnRoute:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route2));

                RouteFragment routeFragment = new RouteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, routeFragment)
                        .commit();
                routeFragment.setInterfaceRoute(new RouteFragment.InterfaceRoute() {
                    @Override
                    public void getRoute(String route) {
                        Toast.makeText(DashboardActivity.this, route, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }

    }
}
