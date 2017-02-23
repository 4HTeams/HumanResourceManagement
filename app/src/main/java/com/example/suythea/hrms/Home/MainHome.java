package com.example.suythea.hrms.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suythea.hrms.MainActivity;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Interfaces.Main_Interface;


public class MainHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }

    public MainHome (){
    }


}
