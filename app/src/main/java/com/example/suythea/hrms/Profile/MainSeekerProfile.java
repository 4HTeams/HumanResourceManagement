package com.example.suythea.hrms.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suythea.hrms.Account.MainUpgradeToCom;
import com.example.suythea.hrms.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainSeekerProfile extends Fragment {

    Button btnUpgradeToCom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_seeker_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setControls();
        setEvents();
        startUp();

    }

    void setControls (){

        btnUpgradeToCom = (Button)getActivity().findViewById(R.id.btnUpgradeToCom);

    }

    void setEvents (){

        btnUpgradeToCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainUpgradeToCom.class);
                startActivity(intent);
            }
        });

    }

    void startUp (){

    }


}
