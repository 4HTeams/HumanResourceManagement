package com.example.suythea.hrms.PostCV;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.Supporting_Files.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

public class MainPostCV extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    String order;
    JSONArray pro, l_lvl, degree, contractType, job_cate;

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

        order = getIntent().getStringExtra("order");
        this.setTitle(order);

        MySqlite sqlite = new MySqlite(this);

        String pro = sqlite.getDataFromjsonField(MySqlite.fields.get(3), "_all_db");
        String l_lvl = sqlite.getDataFromjsonField(MySqlite.fields.get(4), "_all_db");
        String degree = sqlite.getDataFromjsonField(MySqlite.fields.get(5), "_all_db");
        String contract_type = sqlite.getDataFromjsonField(MySqlite.fields.get(6), "_all_db");
        String job_cate = sqlite.getDataFromjsonField(MySqlite.fields.get(7), "_all_db");

        if (pro.equals("") || l_lvl.equals("") || degree.equals("") || contract_type.equals("") || job_cate.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            // Set data to array
        }

        Log.d("result", pro + l_lvl + degree + contract_type + job_cate);

        getSpinnerDB();
    }

    void getSpinnerDB(){
        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php?appToken=ThEa331RA369RiTH383thY925&province=1&lan_lvl=1&degree=1&contractType=1&job_cate=1",new HashMap<String, String>(),this);
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
            String data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");

            String pro = new JSONArray(data).getJSONObject(0).getString("provinces");
            String l_lvl = new JSONArray(data).getJSONObject(0).getString("lan_lvl");
            String degree = new JSONArray(data).getJSONObject(0).getString("degree");
            String contractType = new JSONArray(data).getJSONObject(0).getString("contractType");
            String job_cate = new JSONArray(data).getJSONObject(0).getString("job_cate");

            this.pro = new JSONArray(pro);
            this.l_lvl = new JSONArray(l_lvl);
            this.degree = new JSONArray(degree);
            this.contractType = new JSONArray(contractType);
            this.job_cate = new JSONArray(job_cate);

            Log.d("ABCABC", degree);

            MySqlite sqlite = new MySqlite(this);
            sqlite.insertJsonDB(MySqlite.fields.get(3), pro);
            sqlite.insertJsonDB(MySqlite.fields.get(4), l_lvl);
            sqlite.insertJsonDB(MySqlite.fields.get(5), degree);
            sqlite.insertJsonDB(MySqlite.fields.get(6), contractType);
            sqlite.insertJsonDB(MySqlite.fields.get(7), job_cate);

            // Load Spinners

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {
        MySupporter.hideLoading();
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
