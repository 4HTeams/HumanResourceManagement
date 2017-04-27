package com.example.suythea.hrms.PostCV;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.Supporting_Files.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainPostCV extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    String order;
    JSONArray pro, l_lvl, degree, contractType, job_cate;
    ListView lisAccc;
    Button btnAddACCC;
    Spinner spinGander, spinProvince;
    ArrayList<ListACCCModel> lisAcccModels;

    ListACCCAdp lisAcccAdp;

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
        lisAccc = (ListView)findViewById(R.id.lisACCCPostCV);
        btnAddACCC = (Button)findViewById(R.id.btnAddACCCPostCV);
        spinGander = (Spinner)findViewById(R.id.spinGanderPostCV);
        spinProvince = (Spinner)findViewById(R.id.spinProvincePostCV);
    }

    void setEvents(){
        btnAddACCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String date = df.format(Calendar.getInstance().getTime());

                ListACCCModel model = new ListACCCModel("ABCBABCBCBC", date);
                lisAcccModels.add(model);
                lisAcccAdp.notifyDataSetChanged();
                setFullHeightListView(lisAccc);
            }
        });
    }

    void startUp(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        this.pro = new JSONArray();
        this.l_lvl = new JSONArray();
        this.degree = new JSONArray();
        this.contractType = new JSONArray();
        this.job_cate = new JSONArray();

        lisAcccModels = new ArrayList<>();

        lisAcccAdp = new ListACCCAdp(this, R.layout.list_accc_postcv, lisAcccModels);
        lisAccc.setAdapter(lisAcccAdp);

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

            try {

                this.pro = new JSONArray(pro);
                this.l_lvl = new JSONArray(l_lvl);
                this.degree = new JSONArray(degree);
                this.contractType = new JSONArray(contract_type);
                this.job_cate = new JSONArray(job_cate);

            } catch (JSONException e) {e.printStackTrace();}

            loadSpinners();

        }

        getSpinnerDB();
    }

    void loadSpinners () {

        try{

            int i = 0;
            ArrayList<String> gander, provinces;

            gander = new ArrayList<>();
            gander.add("Male");
            gander.add("Female");

            provinces = new ArrayList<>();

            while (i < pro.length()) {

                provinces.add(pro.getJSONObject(i).getString("proName"));
                i++;
            }

            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gander);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinGander.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinces);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinProvince.setAdapter(adapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getSpinnerDB(){
        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php?appToken=ThEa331RA369RiTH383thY925&province=1&lan_lvl=1&degree=1&contractType=1&job_cate=1",new HashMap<String, String>(),this);
    }

    void setFullHeightListView (ListView listView){
        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
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

            if (this.pro.length() < 1 || this.l_lvl.length() < 1 || this.degree.length() < 1 || this.contractType.length() < 1 || this.job_cate.length() < 1){

                this.pro = new JSONArray(pro);
                this.l_lvl = new JSONArray(l_lvl);
                this.degree = new JSONArray(degree);
                this.contractType = new JSONArray(contractType);
                this.job_cate = new JSONArray(job_cate);

                loadSpinners();
            }

            MySqlite sqlite = new MySqlite(this);
            sqlite.insertJsonDB(MySqlite.fields.get(3), pro);
            sqlite.insertJsonDB(MySqlite.fields.get(4), l_lvl);
            sqlite.insertJsonDB(MySqlite.fields.get(5), degree);
            sqlite.insertJsonDB(MySqlite.fields.get(6), contractType);
            sqlite.insertJsonDB(MySqlite.fields.get(7), job_cate);


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

        if (this.pro.length() < 1 || this.l_lvl.length() < 1 || this.degree.length() < 1 || this.contractType.length() < 1 || this.job_cate.length() < 1) {
            // No data in database and no error getting data from web ---> finish this activity
            Toast.makeText(this,MySupporter.checkError(),Toast.LENGTH_LONG).show();
            finish();
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
