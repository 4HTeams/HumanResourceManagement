package com.example.suythea.hrms.Setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.Profile.MainComProfile;
import com.example.suythea.hrms.Profile.MainSeekerProfile;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Interfaces.Main_Interface;


public class MainSetting extends Fragment implements Setting_Interface {

    public static MainSetting context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (context == null){
            context = this;
            android.support.v4.app.FragmentManager manager = getFragmentManager();
            Setting_Choice setting_choice = new Setting_Choice();
            manager.beginTransaction().replace(R.id.mainContent, setting_choice).commit();
        }
    }

    @Override
    public void changeToFragment(String fragmentName) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (fragmentName.toUpperCase() == "SEEKER_PROFILE"){

            MainSeekerProfile mainSeekerProfile = new MainSeekerProfile();
            transaction.replace(R.id.mainContent, mainSeekerProfile);

        }
        else if (fragmentName.toUpperCase() == "COMPANY_PROFILE"){

            MainComProfile mainComProfile = new MainComProfile();
            transaction.replace(R.id.mainContent, mainComProfile);
        }
        else if (fragmentName.toUpperCase() == "SETTING_CHOICE"){
            Setting_Choice setting_choice = new Setting_Choice();
            transaction.replace(R.id.mainContent, setting_choice);
        }

        transaction.commitAllowingStateLoss();

    }
}
