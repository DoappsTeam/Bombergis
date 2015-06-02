package me.doapps.bombergis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import me.doapps.bombergis.R;

/**
 * Created by user on 27/05/2015.
 */
public class RouteFragment extends Fragment implements View.OnClickListener{
    private Button btnBscRuta;
    private Button btnBscRuta2;
    private InterfaceRoute interfaceRoute;
    private EditText txtOrigen;
    private EditText txtDestino;
    private AutoCompleteTextView autoCompleteTextView;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil", "Padua,Italy", "Pasadena,CA,United States"};
    ArrayAdapter<String> adapter;
    public RouteFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        btnBscRuta = (Button)view.findViewById(R.id.btnBscRuta);
        btnBscRuta2 = (Button)view.findViewById(R.id.btnBscRuta2);
        txtOrigen = (EditText)view.findViewById(R.id.txtOrigen);
        txtDestino = (EditText)view.findViewById(R.id.txtDestino);
       /* autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);

        adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);/*/

        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        btnBscRuta.setOnClickListener(this);
        btnBscRuta2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnBscRuta){interfaceRoute.getRoute(1,txtOrigen.getText().toString());}
        if(v.getId() == R.id.btnBscRuta2){interfaceRoute.getRoute(2, txtDestino.getText().toString());}

    }

    /** Interference **/
    public interface InterfaceRoute{
        void getRoute(int s, String ruta);
    }
    public void setInterfaceRoute(InterfaceRoute interfaceRoute){
        this.interfaceRoute=interfaceRoute;
    }

}
