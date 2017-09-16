package com.example.suythea.hrms.Setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suythea.hrms.Account.MainCreateUser;
import com.example.suythea.hrms.Account.MainLogIn;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Reset_Password.Main_ResetPassword;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setting_Choice extends Fragment {

    Button btnCreateUser, btnLogIn, btnReset;

    public Setting_Choice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_choise, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        btnCreateUser = (Button) getActivity().findViewById(R.id.btnCreateUser);
        btnLogIn=(Button) getActivity().findViewById(R.id.btnLogIn);
        btnReset = (Button) getActivity().findViewById(R.id.btnReset);

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

                Intent intent = new Intent(getActivity(), MainLogIn.class);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Main_ResetPassword.class);
                startActivity(intent);
            }
        });
    }
}
