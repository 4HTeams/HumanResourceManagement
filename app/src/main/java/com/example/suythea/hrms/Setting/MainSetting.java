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
import android.widget.Button;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.Profile.MainComProfile;
import com.example.suythea.hrms.Profile.MainSeekerProfile;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Interfaces.Main_Interface;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class MainSetting extends Fragment implements Setting_Interface, MySupporter_Interface {

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
        loadFragmentByDB();
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

    @Override
    public void loadFragmentByDB(){
        MySqlite sqlite = new MySqlite(getActivity());

        String result = sqlite.getDataFromjsonField(MySqlite.tables.get(0),"type");
        String username = sqlite.getDataFromjsonField(MySqlite.tables.get(0),"username");
        String password = sqlite.getDataFromjsonField(MySqlite.tables.get(0),"password");

        if (result.equals("1") || result.equals("2")){
            reLogin(username, password);
        }
        else {
            changeToFragment("SETTING_CHOICE");
        }

    }

    private void reLogin(String username, String password){

        try {
            username = URLEncoder.encode(username, "utf-8");
            password = URLEncoder.encode(password, "utf-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        params.put("appToken","ThEa331RA369RiTH383thY925");
        params.put("username",username);
        params.put("password",password);

        MySupporter.Volley("http://bongNU.khmerlabs.com/bongNU/Account/login.php", params, this);

    }

    @Override
    public void onFinished(String response) {

        try {
            JSONArray arrayDB = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8"));

            JSONObject jsonObj = arrayDB.getJSONObject(0);

            if (jsonObj.getString("status").equals("Success")){
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.insertUser(response);

                if (jsonObj.getString("type").equals("1")){
                    changeToFragment("SEEKER_PROFILE");
                }
                else if (jsonObj.getString("type").equals("2")){
                    changeToFragment("COMPANY_PROFILE");
                }

            }else{
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.deleteField(MySqlite.tables.get(0));

                changeToFragment("SETTING_CHOICE");
                MySupporter.showSnackBar("Password has been changed !");
            }

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {

    }
}
