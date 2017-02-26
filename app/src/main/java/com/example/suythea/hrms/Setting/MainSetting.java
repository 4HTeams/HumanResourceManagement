package com.example.suythea.hrms.Setting;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.Profile.MainComProfile;
import com.example.suythea.hrms.Profile.MainSeekerProfile;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Interfaces.Main_Interface;
import com.example.suythea.hrms.Supporting_Files.MySqlite;


public class MainSetting extends Fragment implements Setting_Interface {

    public static MainSetting context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = this;
        MySqlite sqlite = new MySqlite(getActivity());
        String result = sqlite.getDataFromjsonField(MySqlite.tables.get(0),"type");
        Log.d("result", result);

        android.support.v4.app.FragmentManager manager = getFragmentManager();
        Object fragment;

        if (result.equals("1")){
            fragment = new MainSeekerProfile();
        }
        else if (result.equals("2")){
            fragment = new MainComProfile();
        }
        else {
            fragment = new Setting_Choice();
        }

        manager.beginTransaction().replace(R.id.mainContent, (Fragment) fragment).commitAllowingStateLoss();
    }

    @Override
    public void changeToFragment(String fragmentName) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (fragmentName.toUpperCase().equals("SEEKER_PROFILE")){

            MainSeekerProfile mainSeekerProfile = new MainSeekerProfile();
            transaction.replace(R.id.mainContent, mainSeekerProfile);

        }
        else if (fragmentName.toUpperCase().equals("COMPANY_PROFILE")){

            MainComProfile mainComProfile = new MainComProfile();
            transaction.replace(R.id.mainContent, mainComProfile);
        }
        else if (fragmentName.toUpperCase().equals("SETTING_CHOICE")){
            Setting_Choice setting_choice = new Setting_Choice();
            transaction.replace(R.id.mainContent, setting_choice);
        }

        transaction.commitAllowingStateLoss();

    }
}
