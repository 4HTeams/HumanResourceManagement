package com.example.suythea.hrms.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.Main_Interface;


public class MainHome extends Fragment {


    public MainHome(Main_Interface main_interface) {
        main_interface.runListener("I am from MainHome !");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }

}
