package com.example.suythea.hrms.Setting;


import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.suythea.hrms.MainActivity;
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
    public static boolean showingLoading = true;
    public static boolean finishedAllData = false;
    public static String username, password, type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = this;
        getDataSQLite ();

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

    void getDataSQLite (){
        MySqlite sqlite = new MySqlite(getActivity());

        type = sqlite.getDataFromjsonField(MySqlite.fields.get(0),"type");
        username = sqlite.getDataFromjsonField(MySqlite.fields.get(0),"username");
        password = sqlite.getDataFromjsonField(MySqlite.fields.get(0),"password");
    }

    public static void checkReloginFromInterface (){
        if (type.equals("")){
            // Type is equal to "" which means no account logged in and stored in database

            // finishedAllData is equal to true here means no account logged in
            finishedAllData = true;

            FragmentTransaction transaction = context.getFragmentManager().beginTransaction();

            Setting_Choice setting_choice = new Setting_Choice();
            transaction.replace(R.id.mainContent, setting_choice);

            transaction.commitAllowingStateLoss();

            MySupporter.hideLoading();
        }
        else {
            reLogin(username, password);
        }
    }

    static void reLogin(String username, String password){

        // It is called from MainActivity to check if old account has a true password or not

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

        MySupporter.Volley("http://bongNU.khmerlabs.com/bongNU/Account/login.php", params, context);

    }

    @Override
    public void onHttpFinished(String response) {

    }

    @Override
    public void onHttpError(String message) {

    }

    @Override
    public void onVolleyFinished(String response) {
        try {
            JSONArray arrayDB = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8"));

            JSONObject jsonObj = arrayDB.getJSONObject(0);

            if (jsonObj.getString("status").equals("Success")){

                // Insert data we got from web service to SQLite
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.insertJsonDB(MySqlite.fields.get(0), response);

                if (jsonObj.getString("type").equals("1")){

                    // If jsonObj.getString("type").equals("1") means this account is a typical user and load fragment as position
                    changeToFragment("SEEKER_PROFILE");
                }
                else if (jsonObj.getString("type").equals("2")){

                    // If jsonObj.getString("type").equals("2") means this account is a company user and load fragment as position
                    changeToFragment("COMPANY_PROFILE");
                }

            }else{
                // If password has been changed, account was logged in is cleared from database and automatically log out

                // It is used to delete old account logged in in database
                MySqlite sqlite = new MySqlite(getActivity());
                sqlite.deleteField(MySqlite.fields.get(0));

                // It is used to change fragment
                changeToFragment("SETTING_CHOICE");

                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "Password has been changed !", Snackbar.LENGTH_LONG).show();
            }

            showingLoading = false;

            // finishedAllData is equal to true here means login or re-login has been successful
            finishedAllData = true;

            MySupporter.hideLoading();

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolleyError(String message) {
        // We don't need to change finishedAllData to true because we are not successful yet

        MySupporter.hideLoading();

        // If it has any errors, don't load any fragments and change tap to HomePage
        Main_Interface main_interface = (Main_Interface) MainActivity.context;
        main_interface.changeTapIndex(0);

        // MySupporter.checkError() means to check why it got this error and whether it is because of Internet or our site problem
        MySupporter.checkError();
    }
}
