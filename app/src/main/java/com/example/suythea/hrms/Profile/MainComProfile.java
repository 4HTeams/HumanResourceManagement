package com.example.suythea.hrms.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.suythea.hrms.Account.MainEditUpgradeToCom;
import com.example.suythea.hrms.Account.MainEditUser;
import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.PostJob.MainPostJob;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.ViewOwnJob.MainViewOwnJob;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class MainComProfile extends Fragment {

    Button btnSignOut, btnUpdate, btnAccUpdate, btnPost, btnView;
    Setting_Interface setting_interface;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    @Override
    public void onResume() {
        super.onResume();
        startUp();
    }

    void setControls (){

        btnSignOut = (Button)getActivity().findViewById(R.id.btnSignOutComPro);
        btnUpdate = (Button)getActivity().findViewById(R.id.btnEditProComPro);
        btnAccUpdate = (Button)getActivity().findViewById(R.id.btnEditUserComPro);
        btnPost = (Button)getActivity().findViewById(R.id.btnPostJobProComPro);
        btnView = (Button)getActivity().findViewById(R.id.btnViewJobProComPro);
        img = (ImageView)getActivity().findViewById(R.id.imgProfileMainCom);

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainEditUpgradeToCom.class);
                intent.putExtra("order", "UPDATE");
                startActivity(intent);
            }
        });

        btnAccUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainEditUser.class);
                startActivity(intent);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainPostJob.class);
                i.putExtra("order","POST");
                startActivity(i);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainViewOwnJob.class);
                i.putExtra("order","POST");
                startActivity(i);
            }
        });

    }

    void startUp (){

        img.setImageResource(getActivity().getResources().getIdentifier("no_profile","mipmap",getActivity().getPackageName()));

        Picasso.with(getActivity())
                .load("http://bongnu.myreading.xyz/profile_images/" + new MySqlite(getActivity()).getDataFromjsonField(MySqlite.fields.get(0),"id") + ".jpg")
                .placeholder(getActivity().getResources().getIdentifier("no_profile","mipmap",getActivity().getPackageName()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

}
