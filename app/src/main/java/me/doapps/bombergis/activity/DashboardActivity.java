package me.doapps.bombergis.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    static ArrayList<Marker> markerHospitales;
    static ArrayList<Marker> markerColegios;
    static ArrayList<Marker> markerHidrantes;
    static ArrayList<Marker> markerBomberos;
    static ArrayList<Marker> markerGrifos;
    static ArrayList<Marker> markerComisarias;

    private List<LatLng> hospitales;
    private List<LatLng> colegios;
    private List<LatLng> hidrantes;
    private List<LatLng> bomberos;
    private List<LatLng> grifos;
    private List<LatLng> comisarias;

    static int stateMapsDashboard = 1;
    private SearchFragment searchFragment;
    private RouteFragment routeFragment;
    private MapOperation mapOperation;
    CameraPosition camPos;
    CameraUpdate camUpd3;
    static Marker markerS;
    static Marker markerO;
    static Marker markerD;

    /**
     * variable floatButton*
     */
    private FloatingActionButton floatingActionButton;

    static LatLng originLaLng;
    static LatLng destinationLaLng;

    public static ArrayList<LatLng> startLocation;
    public static ArrayList<LatLng> endLocation;
    public static ArrayList<Polyline> routeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnInterest = (ImageButton) findViewById(R.id.btnInterest);
        btnInterest.setOnClickListener(this);

        btnMaps = (ImageButton) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(this);

        btnRoute = (ImageButton) findViewById(R.id.btnRoute);
        btnRoute.setOnClickListener(this);

        /*Inicializar el mapa*/
        setUpMapIfNeeded();
        /*Inicializo Searragment*/
        initSearchFragment();
        /*cargamos markers*/
        loadMarkers();
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

                initSearchFragment();
                break;

            case R.id.btnInterest:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest2));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                initInterestFragment();
                break;

            case R.id.btnMaps:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps2));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                initMapsFragment();
                break;

            case R.id.btnRoute:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route2));

                initRouteFragment();
                break;
        }
    }

    /**
     * Methods and Functions*
     */
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

        camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776, -77.0219219))
                .zoom(16)
                .build();
        camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }

    private void initSearchFragment() {
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, searchFragment).commit();
        //Toast.makeText(getApplicationContext(), "Mensaje de Prueba Primeraaa" + String.valueOf(generalState), Toast.LENGTH_SHORT).show();

        //if (generalState == 4 || generalState == 1) {
         //   map.clear();
        //}
        /* Actualizar datos */
        // Si hubo cambios, entonces actualizar. De lo contrario no se actualiza.

        //Actualizar();

        searchFragment.setInterfaceSearch(new SearchFragment.InterfaceSearch() {
            @Override
            public void getAddress(String address) {

                mapOperation = new MapOperation();
                mapOperation.getLocation(address);
                mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                    @Override
                    public void getLocation(String status, Double lat, Double lng) {
                        if (status.equals("OK")) {

                            if (markerS != null) {
                                markerS.remove();
                            }
                            if (markerO != null) {
                                markerO.remove();
                            }
                            if (markerD != null) {
                                markerD.remove();
                            }

                            if (routeList != null) {
                                for (int k = 0; k < routeList.size(); k++)
                                    routeList.get(k).remove();
                            }

                            markerS = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lng))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            //lati=lat;
                            //longi=lng;
                            camPos = new CameraPosition.Builder()
                                    .target(new LatLng(lat, lng))
                                    .zoom(16)
                                    .build();
                            camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                            map.animateCamera(camUpd3);
                        }
                    }
                });
            }
        });
        //generalState = 1;
    }

    private void initInterestFragment(){

        InterestFragment interestFragment = new InterestFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, interestFragment)
                .commit();

        interestFragment.setInterfaceInstitutes(new InterestFragment.InterfaceInstitutes() {
            //(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            @Override
            public void getInstitute(int flag1, int flag2, int flag3, int flag4, int flag5, int flag6) {

                if (markerHospitales != null) {
                    for (int k = 0; k < markerHospitales.size(); k++) {
                        markerHospitales.get(k).remove();
                    }
                }

                if (markerColegios != null) {
                    for (int k = 0; k < markerColegios.size(); k++) {
                        markerColegios.get(k).remove();
                    }
                }

                if (markerHidrantes != null) {
                    for (int k = 0; k < markerHidrantes.size(); k++) {
                        markerHidrantes.get(k).remove();
                    }
                }

                if (markerBomberos != null) {
                    for (int k = 0; k < markerBomberos.size(); k++) {
                        markerBomberos.get(k).remove();
                    }
                }

                if (markerGrifos != null) {
                    for (int k = 0; k < markerGrifos.size(); k++) {
                        markerGrifos.get(k).remove();
                    }
                }

                if (markerComisarias != null) {
                    for (int k = 0; k < markerComisarias.size(); k++) {
                        markerComisarias.get(k).remove();
                    }
                }


                if (flag1 == 1) {
                    markerHospitales = new ArrayList<Marker>();

                    for (int i = 0; i < hospitales.size(); i++) {
                        Marker tempH = map.addMarker(new MarkerOptions()
                                .position(hospitales.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));
                        markerHospitales.add(tempH);
                    }
                }

                if (flag2 == 1) {
                    markerColegios = new ArrayList<Marker>();

                    for (int i = 0; i < colegios.size(); i++) {
                        Marker tempC = map.addMarker(new MarkerOptions()
                                .position(colegios.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
                        markerColegios.add(tempC);
                    }
                }

                if (flag3 == 1) {
                    markerHidrantes = new ArrayList<Marker>();

                    for (int i = 0; i < hidrantes.size(); i++) {
                        Marker tempHi = map.addMarker(new MarkerOptions()
                                .position(hidrantes.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hydrant)));
                        markerHidrantes.add(tempHi);
                    }
                }

                if (flag4 == 1) {
                    markerBomberos = new ArrayList<Marker>();

                    for (int i = 0; i < bomberos.size(); i++) {
                        Marker tempB = map.addMarker(new MarkerOptions()
                                .position(bomberos.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));
                        markerBomberos.add(tempB);
                    }
                }

                if (flag5 == 1) {
                    markerGrifos = new ArrayList<Marker>();

                    for (int i = 0; i < grifos.size(); i++) {
                        Marker tempG = map.addMarker(new MarkerOptions()
                                .position(grifos.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));
                        markerGrifos.add(tempG);
                    }
                }

                if (flag6 == 1) {
                    markerComisarias = new ArrayList<Marker>();

                    for (int i = 0; i < comisarias.size(); i++) {
                        Marker tempCo = map.addMarker(new MarkerOptions()
                                .position(comisarias.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_policestation)));
                        markerComisarias.add(tempCo);
                    }
                }

            }
        });
    }

    private void initMapsFragment(){
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
    }

    private void initRouteFragment() {
        routeFragment = new RouteFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, routeFragment)
                .commit();
        routeFragment.setInterfaceRoute(new RouteFragment.InterfaceRoute() {
            @Override
            public void getRoute(int s, String ruta) {
                if (s == 1) {

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {
                            if (status.equals("OK")) {

                                if (markerS != null) {
                                    markerS.remove();
                                }
                                if (markerO != null) {
                                    markerO.remove();
                                }

                                if (routeList != null) {
                                    for (int k = 0; k < routeList.size(); k++)
                                        routeList.get(k).remove();
                                }

                                originLaLng = new LatLng(lat, lng);
                                //markerO.setVisible(false);
                                markerO = map.addMarker(new MarkerOptions()
                                        .position(originLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
                                //stateMarkerO = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(originLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            }
                        }
                    });
                }

                if (s == 2) {

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {
                            if (status.equals("OK")) {

                                if (markerS != null) {
                                    markerS.remove();
                                }

                                if (markerD != null) {
                                    markerD.remove();
                                }

                                if (routeList != null) {
                                    for (int k = 0; k < routeList.size(); k++)
                                        routeList.get(k).remove();
                                }

                                destinationLaLng = new LatLng(lat, lng);
                                //markerD.setVisible(false);
                                markerD = map.addMarker(new MarkerOptions()
                                        .position(destinationLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker2)));
                                //stateMarkerD = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(destinationLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            }
                        }
                    });
                }

                if (s == 3) {
                    //map.clear();
                    mapOperation = new MapOperation();
                    mapOperation.getSteps(String.valueOf(originLaLng.latitude) + "," + String.valueOf(originLaLng.longitude), String.valueOf(destinationLaLng.latitude) + "," + String.valueOf(destinationLaLng.longitude));
                    mapOperation.setInterfaceSteps(new MapOperation.InterfaceSteps() {
                        @Override
                        public void getRouteSteps(String status, JSONObject steps) {
                            if (status.equals("OK")) {
                                try {
                                    //JSONArray arraySteps = new JSONArray(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps"));
                                    Log.e("objeto steps", steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").toString());
                                    startLocation = new ArrayList<LatLng>();
                                    endLocation = new ArrayList<LatLng>();
                                    routeList = new ArrayList<Polyline>();

                                    for (int k = 0; k < steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").length(); k++) {
                                        startLocation.add(new LatLng(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("start_location").getDouble("lat"), steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("start_location").getDouble("lng")));
                                        endLocation.add(new LatLng(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("end_location").getDouble("lat"), steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("end_location").getDouble("lng")));
                                        Polyline line = map.addPolyline(new PolylineOptions().
                                                add(startLocation.get(k), endLocation.get(k))
                                                .width(12).color(Color.rgb(150, 40, 27)));
                                        routeList.add(line);

                                    }

                                    camPos = new CameraPosition.Builder()
                                            .target(new LatLng((originLaLng.latitude + destinationLaLng.latitude) / 2, (originLaLng.longitude + destinationLaLng.longitude) / 2))
                                            .zoom(13)
                                            .build();
                                    camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                    map.animateCamera(camUpd3);

                                    //stateMap = 0;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    private void loadMarkers() {

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

    public void Actualizar(){
        if(markerS !=null){markerS.remove();}
    }


    public void Alert(View v) {
        if (v.getId() == R.id.float_button) {
            Toast.makeText(getApplicationContext(), R.string.alert, Toast.LENGTH_SHORT).show();
        }
    }
}
