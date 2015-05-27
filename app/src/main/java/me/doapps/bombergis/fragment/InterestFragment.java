package me.doapps.bombergis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.doapps.bombergis.R;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class InterestFragment extends Fragment {

    public InterestFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest, container, false);
        return view;
    }
}
