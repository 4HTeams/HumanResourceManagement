package com.example.suythea.hrms.SearchJob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.suythea.hrms.Home.ListJobAdp;
import com.example.suythea.hrms.Home.ListJobModel;
import com.example.suythea.hrms.Home.MainHome;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewJob.MainViewJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainSearchJob extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    SearchView searchView;
    ListView listView;
    JSONArray jsonArray;
    ArrayList<ListJobModel> lisData;
    ListSearchAdp adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_job);

        MySupporter.runFirstDefault(this);

        setControls();
        setEvents();
        startUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MySupporter.runFirstDefault(this);
    }

    void setControls(){
        toolbar = (Toolbar)findViewById(R.id.toolBarSearchJob);
        searchView = (SearchView)findViewById(R.id.searchBarJob);
        listView = (ListView)findViewById(R.id.lisSearchJob);
    }

    void setEvents(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                searchJobs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        final MainSearchJob _c = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MySupporter.showLoading("Please wait.....");
                MySupporter.Http2("http://bongnu.khmerlabs.com/bongnu/job/get_one_job.php?appToken=ThEa331RA369RiTH383thY925&jid=" + lisData.get(position).getId() + "&cid=" + lisData.get(position).getCid(), new HashMap<String, String>(), getBaseContext(), (MySupporter_Interface)_c);
            }
        });
    }

    void searchJobs(String search){
        MySupporter.showLoading("Please wait.....");

        HashMap<String, String> map = new HashMap<>();
        map.put("appToken", "ThEa331RA369RiTH383thY925");
        map.put("searchStr", search);

        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/search/search_job.php", map, this);
    }

    void startUp(){
        setSupportActionBar(toolbar);
        setTitle("Search Jobs");

        searchView.setInputType(InputType.TYPE_CLASS_TEXT);

        lisData = new ArrayList<>();
        adp = new ListSearchAdp(this, R.layout.list_job_item, lisData);
        listView.setAdapter(adp);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

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
        try{
            MySupporter.hideLoading();
            Intent intent = new Intent(this, MainViewJob.class);
            intent.putExtra("response", URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            startActivity(intent);
        }
        catch (Exception e){
            Log.d("result", e.getMessage());
        }

        Log.d("result", response);
    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVolleyFinished(String response) {
        MySupporter.hideLoading();

        lisData = new ArrayList<>();
        adp = new ListSearchAdp(this, R.layout.list_job_item, lisData);
        listView.setAdapter(adp);

        try {

            if (new JSONArray(response).getJSONObject(0).getString("status").equals("NoData")){
                Toast.makeText(this, new JSONArray(response).getJSONObject(0).getString("Message"), Toast.LENGTH_LONG).show();
                adp.notifyDataSetChanged();
                return;
            }

            jsonArray = new JSONArray(new JSONArray(response).getJSONObject(0).getString("data"));

            ListJobModel model;

            for (int i = 0; i < jsonArray.length(); i++){
                model = new ListJobModel(jsonArray.getJSONObject(i).getString("uid"), jsonArray.getJSONObject(i).getString("title"), jsonArray.getJSONObject(i).getString("deadline"), jsonArray.getJSONObject(i).getString("yearEx"), jsonArray.getJSONObject(i).getString("cName"), jsonArray.getJSONObject(i).getString("salary"), jsonArray.getJSONObject(i).getString("jid"), jsonArray.getJSONObject(i).getString("cid"));
                lisData.add(model);
            }

            adp.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolleyError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }
}
