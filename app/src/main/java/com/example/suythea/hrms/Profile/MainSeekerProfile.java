package com.example.suythea.hrms.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.Main_Interface;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainSeekerProfile extends Fragment {


    public MainSeekerProfile(Main_Interface main_interface) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_seeker_profile, container, false);
    }

}
