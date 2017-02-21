package com.example.suythea.hrms.CV;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suythea.hrms.Interfaces.Main_Interface;
import com.example.suythea.hrms.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainCV extends Fragment {


    public MainCV(Main_Interface main_interface) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_cv, container, false);
    }

}
