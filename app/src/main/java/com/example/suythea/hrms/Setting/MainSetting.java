package com.example.suythea.hrms.Setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suythea.hrms.MainCreateUser;
import com.example.suythea.hrms.MainLogIn;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.Main_Interface;


public class MainSetting extends Fragment implements Setting_Interface{
    Button btnCreateUser,btnLogIn;


    public MainSetting(Main_Interface main_interface) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        btnCreateUser = (Button) getActivity().findViewById(R.id.btnCreateUser);
        btnLogIn=(Button) getActivity().findViewById(R.id.btnLogIn);
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainCreateUser.class);
                startActivity(intent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), MainLogIn.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void runListener(String stringTest) {
        Toast.makeText(getActivity(),stringTest,Toast.LENGTH_LONG).show();
    }
}
