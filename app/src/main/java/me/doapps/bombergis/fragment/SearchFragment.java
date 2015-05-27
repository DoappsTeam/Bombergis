package me.doapps.bombergis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.doapps.bombergis.R;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private Button btnBuscar;
    private InterfaceSearch interfaceSearch;
    private EditText txtAddress;
    public SearchFragment(){}

    @Nullable
    @Override
    /*Conectar el Fragment con el layout*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btnBuscar=(Button) view.findViewById(R.id.btnBuscar);
        txtAddress=(EditText)view.findViewById(R.id.txtAddress);
        return view;
    }
/*Para manipular los elementos del layout*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            btnBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        interfaceSearch.getAddress(txtAddress.getText().toString());
    }

    public interface InterfaceSearch{
        void getAddress(String address);
    }
    public void setInterfaceSearch(InterfaceSearch interfaceSearch){
        this.interfaceSearch=interfaceSearch;
    }
}
