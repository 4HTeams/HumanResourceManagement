package com.example.suythea.hrms.PostJob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;

public class MainPostJob extends AppCompatActivity implements MySupporter_Interface{

    JSONArray jCate;
    JSONArray conTypes;
    JSONArray c_lvl;
    JSONArray pro;
    JSONArray degree;

    String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_job);

        MySupporter.runFirstDefault(this);

        setControls();
        setEvents();
        startUp();
    }

    void setControls(){

    }

    void setEvents(){

    }

    void startUp(){

        order = getIntent().getStringExtra("order");

        MySqlite sqlite = new MySqlite(this);

        String jCate = sqlite.getDataFromjsonField(MySqlite.fields.get(7), "_all_db");
        String conTypes = sqlite.getDataFromjsonField(MySqlite.fields.get(6), "_all_db");
        String c_lvl = sqlite.getDataFromjsonField(MySqlite.fields.get(8), "_all_db");
        String pro = sqlite.getDataFromjsonField(MySqlite.fields.get(3), "_all_db");
        String degree = sqlite.getDataFromjsonField(MySqlite.fields.get(5), "_all_db");

        if (jCate.equals("") || conTypes.equals("") || c_lvl.equals("") || pro.equals("") || degree.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            try {

                this.jCate = new JSONArray(jCate);
                this.conTypes = new JSONArray(conTypes);
                this.c_lvl = new JSONArray(c_lvl);
                this.pro = new JSONArray(pro);
                this.degree = new JSONArray(degree);

                loadSpinner();

                if (order.equals("UPDATE")){
                    setOldData();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        dataVolleyForFields();
    }

    void dataVolleyForFields(){

    }

    void loadSpinner(){

    }

    void setOldData(){

    }

    @Override
    public void onHttpFinished(String response) {

    }

    @Override
    public void onHttpError(String message) {

    }

    @Override
    public void onVolleyFinished(String response) {

    }

    @Override
    public void onVolleyError(String message) {

    }
}
