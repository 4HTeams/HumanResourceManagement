package com.example.suythea.hrms.Profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Supporting_Files.MySqlite;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainComProfile extends Fragment {

    Button btnSignOut;
    Setting_Interface setting_interface;

    public MainComProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_com_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setting_interface = MainSetting.context;

        setControls();
        setEvents();
        startUp();

    }

    void setControls (){

        btnSignOut = (Button)getActivity().findViewById(R.id.btnSignOutComPro);

    }

    void setEvents (){

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.deleteField(MySqlite.fields.get(0));
                setting_interface.changeToFragment("SETTING_CHOICE");
            }
        });

    }

    void startUp (){

    }

}
