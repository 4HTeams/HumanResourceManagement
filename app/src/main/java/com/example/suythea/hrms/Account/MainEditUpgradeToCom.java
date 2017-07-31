package com.example.suythea.hrms.Account;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
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
    JSONObject jsonCompanyInfo;

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

        industries = new JSONArray();
        cTypes = new JSONArray();
        jsonCompanyInfo = new JSONObject();

        order = getIntent().getStringExtra("order");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        setSupportActionBar(toolbar);
        setTitle(order);

        MySqlite sqlite = new MySqlite(this);

        String industry = sqlite.getDataFromjsonField(MySqlite.fields.get(1), "_all_db");
        String cType = sqlite.getDataFromjsonField(MySqlite.fields.get(2), "_all_db");

        if (industry.equals("") || cType.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            try {

                industries = new JSONArray(industry);
                cTypes = new JSONArray(cType);

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

    private void setOldData (){

        try {

            MySqlite sqlite = new MySqlite(this);
            jsonCompanyInfo = new JSONArray(sqlite.getDataFromjsonField(MySqlite.fields.get(0), "_all_db")).getJSONObject(0).getJSONArray("companyInfo").getJSONObject(0);

            eTCName.setText(jsonCompanyInfo.getString("cName"));
            eTCEmail.setText(jsonCompanyInfo.getString("cEmail"));
            eTCAddress.setText(jsonCompanyInfo.getString("address"));
            eTCEmpAmount.setText(jsonCompanyInfo.getString("empAmount"));
            eTCAbout.setText(jsonCompanyInfo.getString("about"));
            eTCContact.setText(jsonCompanyInfo.getString("contact"));

            for(int i = 0; i < industries.length(); i++){
                if (jsonCompanyInfo.getString("industry").toUpperCase().equals(industries.getJSONObject(i).getString("name").toUpperCase())){
                    spiIndustry.setSelection(i);
                    break;
                }
            }

            for(int j = 0; j < cTypes.length(); j++){
                if (jsonCompanyInfo.getString("cType").toUpperCase().equals(cTypes.getJSONObject(j).getString("type").toUpperCase())){
                    spiCType.setSelection(j);
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);

                params = new HashMap<>();

                if (order.equals("UPGRADE")){
                    dataHttpToUpgrade();
                }
                else if (order.equals("UPDATE")){
                    dataHttpToUpdate();
                }
                break;
            case android.R.id.home :
                finish();
                break;
        }
        return true;
    }

    private String checkFields (){

        String result = "Nothing Changed !";

        try {

            String industryID = String.valueOf(industries.getJSONObject((Integer) spiIndustry.getSelectedItemPosition()).getString("id"));
            String cTypeID = String.valueOf(cTypes.getJSONObject((Integer) spiCType.getSelectedItemPosition()).getString("id"));

            MySqlite sqlite = new MySqlite(this);
            params.put("appToken","ThEa331RA369RiTH383thY925");
            params.put("id",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"id"));
            params.put("orderToDo",order);
            params.put("conPassword",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"password"));

            if (!eTCName.getText().toString().equals(jsonCompanyInfo.getString("cName"))){
                params.put("cName", eTCName.getText().toString());
                result = "OK";
            }
            if (!eTCEmail.getText().toString().equals(jsonCompanyInfo.getString("cEmail"))){
                params.put("cEmail", eTCEmail.getText().toString());
                result = "OK";
            }
            if (!eTCEmpAmount.getText().toString().equals(jsonCompanyInfo.getString("empAmount"))){
                params.put("empAmount", eTCEmpAmount.getText().toString());
                result = "OK";
            }
            if (!eTCAddress.getText().toString().equals(jsonCompanyInfo.getString("address"))){
                params.put("address", eTCAddress.getText().toString());
                result = "OK";
            }
            if (!eTCContact.getText().toString().equals(jsonCompanyInfo.getString("contact"))){
                params.put("contact", eTCContact.getText().toString());
                result = "OK";
            }
            if (!eTCAbout.getText().toString().equals(jsonCompanyInfo.getString("about"))){
                params.put("about", eTCAbout.getText().toString());
                result = "OK";
            }
            if (!spiIndustry.getSelectedItem().toString().toUpperCase().equals(jsonCompanyInfo.getString("industry").toUpperCase())){
                params.put("industry", industryID);
                result = "OK";
            }
            if (!spiCType.getSelectedItem().toString().toUpperCase().equals(jsonCompanyInfo.getString("cType").toUpperCase())){
                params.put("cType", cTypeID);
                result = "OK";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void dataHttpToUpdate (){

        // Not checked nothing yet
        if (!checkFields().equals("OK")){
            Snackbar.make(toolbar, "Nothing Changed !", Snackbar.LENGTH_LONG).show();
            return;
        }

        String verifiedResult = MySupporter.verifyControls(params);

        if (!verifiedResult.equals("OK")){
            Snackbar.make(toolbar, verifiedResult, Snackbar.LENGTH_LONG).show();
            return;
        }

        MySupporter.Http("http://bongnu.myreading.xyz/bongnu/account/edit_com.php", params, this);
        MySupporter.showLoading("Please wait.....");
    }

    private void dataHttpToUpgrade (){

        try {

            MySqlite sqlite = new MySqlite(this);
            String industryID = "", cTypeID = "", _eTCName = "", _eTCEmail = "", _eTCEmpAmount = "", _eTCContact = "", _eTCAbout = "", _eTCAddress = "";

            industryID = String.valueOf(industries.getJSONObject((Integer) spiIndustry.getSelectedItemPosition()).getString("id"));
            cTypeID = String.valueOf(cTypes.getJSONObject((Integer) spiCType.getSelectedItemPosition()).getString("id"));

            params.put("appToken","ThEa331RA369RiTH383thY925");
            params.put("id",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"id"));
            params.put("cName",eTCName.getText().toString());
            params.put("cEmail",eTCEmail.getText().toString());
            params.put("industry",industryID);
            params.put("cType",cTypeID);
            params.put("empAmount",eTCEmpAmount.getText().toString());
            params.put("address",eTCAddress.getText().toString());
            params.put("contact",eTCContact.getText().toString());
            params.put("about",eTCAbout.getText().toString());
            params.put("orderToDo",order);
            params.put("conPassword",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"password"));

            String verifiedResult = MySupporter.verifyControls(params);

            if (!verifiedResult.equals("OK")){
                Snackbar.make(toolbar, verifiedResult, Snackbar.LENGTH_LONG).show();
                return;
            }

            MySupporter.Http("http://bongnu.myreading.xyz/bongnu/account/edit_com.php", params, this);
            MySupporter.showLoading("Please wait.....");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void dataVolleyForFields (){
        Map<String, String> params = new HashMap<>();
        params.put("appToken","ThEa331RA369RiTH383thY925");
        params.put("industry","1");
        params.put("cType","1");

        MySupporter.Volley("http://bongnu.myreading.xyz/bongnu/get_data_tbl.php", params, (MySupporter_Interface) this);
    }

    private void alterJsonUpdate (String response){

        try {

            MySqlite sqlite = new MySqlite(this);
            JSONObject oldData = new JSONArray(sqlite.getDataFromjsonField(MySqlite.fields.get(0),"_all_db")).getJSONObject(0);

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonComInfo = new JSONObject();

            jsonObject.put("id", oldData.getString("id"));
            jsonObject.put("username", oldData.getString("username"));
            jsonObject.put("password", oldData.getString("password"));
            jsonObject.put("email", oldData.getString("email"));
            jsonObject.put("profile_url", oldData.getString("profile_url"));
            jsonObject.put("type", "2");
            jsonObject.put("approval", oldData.getString("approval"));

            jsonComInfo.put("cID", jsonCompanyInfo.getString("cID"));
            jsonComInfo.put("industry", spiIndustry.getSelectedItem());
            jsonComInfo.put("cType", spiCType.getSelectedItem());

            if (params.containsKey("cName")){
                jsonComInfo.put("cName", params.get("cName"));
            }
            else {
                jsonComInfo.put("cName", jsonCompanyInfo.getString("cName"));
            }

            if (params.containsKey("cEmail")){
                jsonComInfo.put("cEmail", params.get("cEmail"));
            }
            else {
                jsonComInfo.put("cEmail", jsonCompanyInfo.getString("cEmail"));
            }

            if (params.containsKey("empAmount")){
                jsonComInfo.put("empAmount", params.get("empAmount"));
            }
            else {
                jsonComInfo.put("empAmount", jsonCompanyInfo.getString("empAmount"));
            }

            if (params.containsKey("address")){
                jsonComInfo.put("address", params.get("address"));
            }
            else {
                jsonComInfo.put("address", jsonCompanyInfo.getString("address"));
            }

            if (params.containsKey("contact")){
                jsonComInfo.put("contact", params.get("contact"));
            }
            else {
                jsonComInfo.put("contact", jsonCompanyInfo.getString("contact"));
            }

            if (params.containsKey("about")){
                jsonComInfo.put("about", params.get("about"));
            }
            else {
                jsonComInfo.put("about", jsonCompanyInfo.getString("about"));
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonComInfo);

            jsonObject.put("companyInfo", jsonArray);

            String finalUserData = "[" + String.valueOf(jsonObject) + "]";

            sqlite.insertJsonDB(MySqlite.fields.get(0), finalUserData);

            Log.d("result123", finalUserData);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        MySupporter.hideLoading();
        finish();
    }

    private void alterJsonUpgrade (String response){

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

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonComInfo);

            jsonObject.put("companyInfo", jsonArray);

            String finalUserData = "[" + String.valueOf(jsonObject) + "]";

            sqlite.insertJsonDB(MySqlite.fields.get(0), finalUserData);

            Log.d("result", finalUserData);

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

        if (order.equals("UPGRADE")){
            alterJsonUpgrade(response);
        }
        else if (order.equals("UPDATE")){
            alterJsonUpdate(response);
        }

    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
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

            if (industries.length() < 1 || cTypes.length() < 1){
                // It is less 1 means 0 means no data in database

                // Convert data into JsonArray to show in combo box
                industries = new JSONArray(industry);
                cTypes = new JSONArray(type);

                loadSpinner();

                if (order.equals("UPDATE")){
                    setOldData();
                }
            }

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {

        if (industries.length() < 1 || cTypes.length() < 1){
            MySupporter.hideLoading();
            Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
            finish();
        }

    }


}
