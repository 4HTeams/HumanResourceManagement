package com.example.suythea.hrms.Setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.suythea.hrms.Supporting_Files.Main_Interface;


import com.example.suythea.hrms.R;


public class MainSetting extends Fragment {


    public MainSetting(Main_Interface main_interface) {
        main_interface.runListener("I am from MainSetting !");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

}
