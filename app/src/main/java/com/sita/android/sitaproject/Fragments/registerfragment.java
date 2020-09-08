package com.sita.android.sitaproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sita.android.sitaproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class registerfragment extends Fragment {


    public registerfragment() {
        // Required empty public constructor
    }

    public static registerfragment newInstance(){

        registerfragment fragment= new registerfragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registerfragment, container, false);
    }

}
