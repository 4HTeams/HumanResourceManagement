package com.example.suythea.hrms.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suythea.hrms.Account.MainEditUpgradeToCom;
import com.example.suythea.hrms.Account.MainEditUser;
import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.PostCV.MainPostCV;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.ViewOwnCV.MainViewOwnCV;


public class MainSeekerProfile extends Fragment {

    Button btnUpgradeToCom, btnSignOut, btnChangeInfo, btnPostCV, btnViewCV;
    Setting_Interface setting_interface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_seeker_profile, container, false);
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

        btnUpgradeToCom = (Button)getActivity().findViewById(R.id.btnUpgradeToCom);
        btnSignOut = (Button)getActivity().findViewById(R.id.btnSignOutSeekerPro);
        btnChangeInfo = (Button)getActivity().findViewById(R.id.btnChangeInfoSeekerPro);
        btnPostCV = (Button)getActivity().findViewById(R.id.btnPostCVInfoSeekerPro);
        btnViewCV = (Button)getActivity().findViewById(R.id.btnViewCVInfoSeekerPro);

    }

    void setEvents (){

        btnUpgradeToCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainEditUpgradeToCom.class);
                intent.putExtra("order", "UPGRADE");
                startActivity(intent);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.deleteField(MySqlite.fields.get(0));
                setting_interface.changeToFragment("SETTING_CHOICE");
            }
        });

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainEditUser.class);
                startActivity(intent);
            }
        });

        btnPostCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainPostCV.class);
                intent.putExtra("order", "POST");
                startActivity(intent);
            }
        });

        btnViewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainViewOwnCV.class);
                startActivity(i);
            }
        });

    }

    void startUp (){

    }


}
