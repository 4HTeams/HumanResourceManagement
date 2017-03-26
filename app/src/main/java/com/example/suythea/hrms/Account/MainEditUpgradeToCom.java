package com.example.suythea.hrms.Account;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Setting.MainSetting;
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
    JSONArray cTypes;
    Spinner spiIndustry, spiCType;
    EditText eTCName, eTCEmail, eTCEmpAmount, eTCContact, eTCAbout, eTCAddress;
    Map<String, String> params;
    LinearLayout myMainContentLayout;

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
        spiCType = (Spinner) findViewById(R.id.spiCTypeEditCom);
        eTCName = (EditText) findViewById(R.id.eTComNameEditUpgradeToCom);
        eTCEmail = (EditText) findViewById(R.id.eTEmailEditUpgradeToCom);
        eTCEmpAmount = (EditText) findViewById(R.id.eTEmpAmountEditUpgradeToCom);
        eTCAddress = (EditText) findViewById(R.id.eTAddressEditUpgradeToCom);
        eTCContact = (EditText) findViewById(R.id.eTContactEditUpgradeToCom);
        eTCAbout = (EditText) findViewById(R.id.eTAboutEditUpgradeToCom);
        myMainContentLayout = (LinearLayout) findViewById(R.id.linContentEditUpgrade);
    }

    void setEvents (){

    }

    void startUp (){
        myMainContentLayout.setVisibility(View.GONE);

        order = getIntent().getStringExtra("order");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        setSupportActionBar(toolbar);
        setTitle(order);

        MySqlite sqlite = new MySqlite(this);

        String industry = sqlite.getDataFromjsonField(MySqlite.fields.get(1), "_all_db");
        String cType = sqlite.getDataFromjsonField(MySqlite.fields.get(2), "_all_db");

        if (industry.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            try {

                industries = new JSONArray(industry);
                cTypes = new JSONArray(cType);

                loadSpinner();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        industries = new JSONArray();
        cTypes = new JSONArray();

        dataVolleyForFields();

    }

    void loadSpinner (){

        List<String> industrySpinner =  new ArrayList<>();
        List<String> cTypeSpinner =  new ArrayList<>();

        try {
            int i = 0;
            while (i < industries.length()) {
                industrySpinner.add(industries.getJSONObject(i).getString("name"));
                i++;
            }

            i = 0;
            while (i < cTypes.length()) {
                cTypeSpinner.add(cTypes.getJSONObject(i).getString("type"));
                i++;
            }

        }catch (JSONException e) {e.printStackTrace();}

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, industrySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiIndustry.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cTypeSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiCType.setAdapter(adapter);

        myMainContentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.icSave :
                dataHttpToUpdateUpgrade();
                break;
            case android.R.id.home :
                finish();
                break;
        }
        return true;
    }

    private void dataHttpToUpdateUpgrade (){

        try {

            MySqlite sqlite = new MySqlite(this);
            String industryID = "", cTypeID = "", _eTCName = "", _eTCEmail = "", _eTCEmpAmount = "", _eTCContact = "", _eTCAbout = "", _eTCAddress = "";

            try {

                industryID = String.valueOf(industries.getJSONObject((Integer) spiIndustry.getSelectedItemPosition()).getString("id"));
                cTypeID = String.valueOf(cTypes.getJSONObject((Integer) spiCType.getSelectedItemPosition()).getString("id"));

                _eTCName = URLEncoder.encode(eTCName.getText().toString(), "utf-8");
                _eTCEmail = URLEncoder.encode(eTCEmail.getText().toString(), "utf-8");
                _eTCEmpAmount = URLEncoder.encode(eTCEmpAmount.getText().toString(), "utf-8");
                _eTCContact = URLEncoder.encode(eTCContact.getText().toString(), "utf-8");
                _eTCAbout = URLEncoder.encode(eTCAbout.getText().toString(), "utf-8");
                _eTCAddress = URLEncoder.encode(eTCAddress.getText().toString(), "utf-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            params = new HashMap<>();
            params.put("appToken","ThEa331RA369RiTH383thY925");
            params.put("id",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"id"));
            params.put("cName",_eTCName);
            params.put("cEmail",_eTCEmail);
            params.put("industry",industryID);
            params.put("cType",cTypeID);
            params.put("empAmount",_eTCEmpAmount);
            params.put("address",_eTCAddress);
            params.put("contact",_eTCContact);
            params.put("about",_eTCAbout);
            params.put("orderToDo",order);
            params.put("conPassword",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"password"));

            String verifiedResult = MySupporter.verifyControls(params);

            if (!verifiedResult.equals("OK")){
                Snackbar.make(toolbar, verifiedResult, Snackbar.LENGTH_LONG).show();
                return;
            }

            MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/account/edit_com.php", params, this);
            MySupporter.showLoading("Please wait.....");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dataVolleyForFields(){
        Map<String, String> params = new HashMap<>();
        params.put("appToken","ThEa331RA369RiTH383thY925");
        params.put("industry","1");
        params.put("cType","1");

        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php", params, (MySupporter_Interface) this);
    }

    private void alterJson (String response){

        try {

            MySqlite sqlite = new MySqlite(this);
            JSONObject oldData = new JSONArray(sqlite.getDataFromjsonField(MySqlite.fields.get(0),"_all_db")).getJSONObject(0);

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonComInfo = new JSONObject();

            String industry = String.valueOf(spiIndustry.getSelectedItem());
            String cType = String.valueOf(spiCType.getSelectedItem());
            String cID = new JSONArray(response).getJSONObject(0).getString("cid");

            jsonObject.put("id", oldData.getString("id"));
            jsonObject.put("username", oldData.getString("username"));
            jsonObject.put("password", oldData.getString("password"));
            jsonObject.put("email", oldData.getString("email"));
            jsonObject.put("profile_url", oldData.getString("profile_url"));
            jsonObject.put("type", "2");
            jsonObject.put("approval", oldData.getString("approval"));

            jsonComInfo.put("cID", cID);
            jsonComInfo.put("cName", params.get("cName"));
            jsonComInfo.put("cEmail", params.get("cEmail"));
            jsonComInfo.put("industry", industry);
            jsonComInfo.put("cType", cType);
            jsonComInfo.put("empAmount", params.get("empAmount"));
            jsonComInfo.put("address", params.get("address"));
            jsonComInfo.put("contact", params.get("contact"));
            jsonComInfo.put("about", params.get("about"));

            jsonObject.put("companyInfo", jsonComInfo);

            String finalUserData = "[" + String.valueOf(jsonObject) + "]";

            sqlite.insertJsonDB(MySqlite.fields.get(0), finalUserData);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MySupporter.hideLoading();

        Setting_Interface setting_interface = MainSetting.context;
        setting_interface.changeToFragment("COMPANY_PROFILE");

        finish();
    }


    @Override
    public void onHttpFinished(String response) {
        alterJson (response);
    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyFinished(String response) {

        String data = "", industry = "", type = "";

        try {

            data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");

            industry = new JSONArray(data).getJSONObject(0).getString("industry");
            type = new JSONArray(data).getJSONObject(0).getString("type");

            // Save to local database
            MySqlite sqlite = new MySqlite(getBaseContext());
            sqlite.insertJsonDB(MySqlite.fields.get(1), industry);
            sqlite.insertJsonDB(MySqlite.fields.get(2), type);

            if (industries.length() < 1){
                // It is less 1 means 0 means no data in database

                // Convert data into JsonArray to show in combo box
                industries = new JSONArray(industry);
                cTypes = new JSONArray(type);
            }

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("result", data);

        loadSpinner();
        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {

        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
        finish();

    }


}
