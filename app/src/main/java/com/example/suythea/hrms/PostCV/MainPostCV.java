package com.example.suythea.hrms.PostCV;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;

public class MainPostCV extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_cv);

        MySupporter.runFirstDefault(this);

        setControls();
        setEvents();
        startUp();
    }

    void setControls(){
        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
    }

    void setEvents(){

    }

    void startUp(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        this.setTitle("Post CV");


        MySqlite sqlite = new MySqlite(this);

        String industry = sqlite.getDataFromjsonField(MySqlite.fields.get(1), "_all_db");
        String cType = sqlite.getDataFromjsonField(MySqlite.fields.get(2), "_all_db");

        if (industry.equals("") || cType.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {

        }
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
