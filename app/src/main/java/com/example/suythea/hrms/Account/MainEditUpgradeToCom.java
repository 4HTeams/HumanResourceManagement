package com.example.suythea.hrms.Account;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainEditUpgradeToCom extends AppCompatActivity {

    Toolbar toolbar;
    String order;
    JSONArray industries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edit_upgrade_to_com);

        MySupporter.runFirstDefault(this);

        setControls();
        setEvents();
        startUp();
    }

    void setControls (){
        toolbar = (Toolbar) findViewById(R.id.toolBarNoSearch);
    }

    void setEvents (){

    }

    void startUp (){
        order = getIntent().getStringExtra("order");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        setSupportActionBar(toolbar);
        setTitle(order);

        MySqlite sqlite = new MySqlite(this);
        String industry = sqlite.getDataFromjsonField(MySqlite.fields.get(1), "_all_db");

        if (industry.equals("")){

        }
        else {
            try {
                industries = new JSONArray(industry);

                int i = 0;
                while (i < industries.length()){

                    JSONObject jsonObj = industries.getJSONObject(i);
                    Snackbar.make(toolbar, jsonObj.getString("name"), Snackbar.LENGTH_LONG).show();

                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case android.R.id.home :
                finish();
                break;

        }

        return true;
    }

}
