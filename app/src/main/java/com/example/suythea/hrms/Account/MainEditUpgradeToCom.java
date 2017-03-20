package com.example.suythea.hrms.Account;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainEditUpgradeToCom extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    String order;
    JSONArray industries;
    Spinner spiIndustry;
    Boolean existingInDB = false;

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
        spiIndustry = (Spinner) findViewById(R.id.spiIndustryEditCom);
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
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            try {
                industries = new JSONArray(industry);
                existingInDB = true;
                loadSpinner();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        dataVolley();

    }

    void loadSpinner (){

        List<String> spinnerArray =  new ArrayList<>();

        try {
            int i = 0;
            while (i < industries.length()) {
                spinnerArray.add(industries.getJSONObject(i).getString("name"));
                i++;
            }
        }catch (JSONException e) {e.printStackTrace();}

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiIndustry.setAdapter(adapter);
    }

    private void dataVolley(){
        Map<String, String> params = new HashMap<>();
        params.put("appToken","ThEa331RA369RiTH383thY925");
        params.put("industry","1");

        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php", params, (MySupporter_Interface) this);
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

    @Override
    public void onHttpFinished(String response) {

    }

    @Override
    public void onHttpError(String message) {

    }

    @Override
    public void onVolleyFinished(String response) {

        String data = "";

        try {

            data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");
            data = new JSONArray(data).getJSONObject(0).getString("data");

            // Save to local database
            MySqlite sqlite = new MySqlite(getBaseContext());
            sqlite.insertJsonDB(MySqlite.fields.get(1), data);

            if (!existingInDB){
                // It is false means not to load data to industries again

                // Convert data into JsonArray to show in combo box
                industries = new JSONArray(data);
            }

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("response", String.valueOf(industries));

        loadSpinner();
        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {
        MySupporter.hideLoading();
    }
}
