package com.example.suythea.hrms.CV;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import java.util.ArrayList;

public class MainCV extends Fragment implements MySupporter_Interface{

    ListView lisView;
    static ArrayList<ListCVModel> lisData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_cv, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        MySupporter.runFirstDefault(getActivity());

        setControls();
        setEvents();
        startUp();
    }

    void setControls(){
        lisView = (ListView)getActivity().findViewById(R.id.lisCV);
    }

    void setEvents(){

    }

    void startUp(){
        lisData = new ArrayList<>();
    }

    public static void startGettingData(){
        if (lisData.size() < 1){
            //
        }
    }

    @Override
    public void onHttpFinished(String response) {

    }

    @Override
    public void onHttpError(String message) {

    }

    @Override
    public void onVolleyFinished(String response) {

    }

    @Override
    public void onVolleyError(String message) {

    }
}
