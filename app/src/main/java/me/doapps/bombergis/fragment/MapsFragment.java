package me.doapps.bombergis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import me.doapps.bombergis.R;

/**
 * Created by user on 27/05/2015.
 */
public class MapsFragment extends Fragment {

    RadioGroup radioMaps;
    RadioButton radioMapsNormal;
    RadioButton radioMapsSatelital;
    RadioButton radioMapsHibrido;

    private InterfaceChangeMaps interfaceChangeMaps;


    public MapsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        radioMaps = (RadioGroup) view.findViewById(R.id.radioMaps);
        radioMapsNormal = (RadioButton) view.findViewById(R.id.radioMapsNormal);
        radioMapsSatelital = (RadioButton) view.findViewById(R.id.radioMapsSatelital);
        radioMapsHibrido = (RadioButton) view.findViewById(R.id.radioMapsHibrido);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        radioMaps.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioMapsNormal:
                    interfaceChangeMaps.getMaps(1);
                    //Toast.makeText(getActivity(), "soy normal", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioMapsSatelital:
                    interfaceChangeMaps.getMaps(2);
                    //Toast.makeText(getActivity(), "soy satelital", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioMapsHibrido:
                    interfaceChangeMaps.getMaps(3);
                    //Toast.makeText(getActivity(), "soy hibrido", Toast.LENGTH_SHORT).show();
                    break;
                    default:
                    break;
            }
          }
         }
     );
    }

    public interface InterfaceChangeMaps {
        void getMaps(int flag);
    }

    public void setInterfaceChangeMaps(InterfaceChangeMaps interfaceChangeMaps) {
        this.interfaceChangeMaps = interfaceChangeMaps;
    }
}
