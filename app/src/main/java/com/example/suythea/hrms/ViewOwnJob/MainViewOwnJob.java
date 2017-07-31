package com.example.suythea.hrms.ViewOwnJob;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.ViewOwn_Job_CV_Interface;
import com.example.suythea.hrms.PostCV.MainPostCV;
import com.example.suythea.hrms.PostJob.MainPostJob;
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

public class MainViewOwnJob extends AppCompatActivity implements MySupporter_Interface, ViewOwn_Job_CV_Interface{

    ListViewOwnJobAdp adp;
    ListView lisViewJob;
    Toolbar toolbar;
    JSONArray jsonData;
    int deletingID;
    int selectedID;
    ArrayList<ListViewOwnJobModel> listJobModels;
    public static String oldJobData;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_own_job);

        MySupporter.runFirstDefault(this);
        context = this;

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
        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
        lisViewJob = (ListView)findViewById(R.id.lisViewOwnJob);
    }

    void setEvents(){
        lisViewJob.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        try {
                            String jobID =  new JSONObject(String.valueOf(new JSONArray(String.valueOf(jsonData)).getJSONObject(position))).getString("jid");

                            switch(item.getItemId()){

                                case R.id.menu_edit_post_cv:
                                    showEditingJob(jobID, position);
                                    break;

                                case R.id.menu_delete_post_cv:
                                    deleteCV(jobID, position);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });

    }

    void startUp(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        setTitle("View Own Job");

        listJobModels = new ArrayList<>();
        adp = new ListViewOwnJobAdp(this, R.layout.list_own_job_item, listJobModels);
        lisViewJob.setAdapter(adp);

        volleyGetOwnJob();
    }

    void deleteCV (String id, int selectedID) throws JSONException {
        deletingID = selectedID;
        MySupporter.showLoading("Please Wait.....");

        HashMap<String, String> params = new HashMap<>();
        params.put("appToken", "ThEa331RA369RiTH383thY925");
        params.put("jid", id);
        params.put("id", new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"id"));
        params.put("conPassword", new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"password"));

        MySupporter.Http("http://bongnu.myreading.xyz/bongnu/job/delete_job.php", params, this);
    }

    void showEditingJob(String id, int selectedID) throws JSONException {
        this.selectedID = selectedID;

        oldJobData = String.valueOf(jsonData.getJSONObject(selectedID));

        Intent i = new Intent(this, MainPostJob.class);
        i.putExtra("order", "EDIT");
        startActivity(i);
    }

    void volleyGetOwnJob(){
        try {

            MySupporter.showLoading("Please Wait.....");
            MySupporter.Volley("http://bongnu.myreading.xyz/bongnu/job/get_own_jobs.php?appToken=ThEa331RA369RiTH383thY925&cid=" + new JSONArray(new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0), "companyInfo")).getJSONObject(0).getString("cID"),new HashMap<String, String>(),this);

        } catch (JSONException e) {
            e.printStackTrace();
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

    @Override
    public void onHttpFinished(String response) {

        try {
            Log.d("result", response);
            MySupporter.hideLoading();
            JSONArray arrayDB = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            JSONObject jsonObj = arrayDB.getJSONObject(0);

            if (jsonObj.getString("status").equals("ErrorPassword")){
                Toast.makeText(this, jsonObj.getString("Message"), Toast.LENGTH_LONG).show();
                return;
            }

            if (jsonObj.getString("status").equals("Success")){
                Toast.makeText(this, "Deleted Successfully.", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonData.remove(deletingID);
                }

                listJobModels.remove(deletingID);
                adp.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVolleyFinished(String response) {

        try {

            listJobModels = new ArrayList<>();
            jsonData = new JSONArray(new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8")).getJSONObject(0).getString("data"));

            Log.d("result", String.valueOf(jsonData));

            for(int i = 0; i< jsonData.length(); i++){
                ListViewOwnJobModel jobModel = new ListViewOwnJobModel(jsonData.getJSONObject(i).getString("job_title"), jsonData.getJSONObject(i).getString("year_experience"), jsonData.getJSONObject(i).getString("salary"), jsonData.getJSONObject(i).getString("application_deadline"));
                listJobModels.add(jobModel);
            }

            adp = new ListViewOwnJobAdp(this, R.layout.list_own_job_item, listJobModels);
            lisViewJob.setAdapter(adp);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void reloadChangedData(Object object) {
        try {

            JSONArray jsonArray = new JSONArray();
            for (int i=0; i<jsonData.length(); i++){
                if (i != selectedID){
                    jsonArray.put(jsonData.get(i));
                    continue;
                }
                jsonArray.put(object);
            }

            jsonData = jsonArray;
            ListViewOwnJobModel cvModel = new ListViewOwnJobModel(jsonData.getJSONObject(selectedID).getString("job_title"), jsonData.getJSONObject(selectedID).getString("year_experience"), jsonData.getJSONObject(selectedID).getString("salary"), jsonData.getJSONObject(selectedID).getString("application_deadline"));
            listJobModels.set(selectedID, cvModel);

            Log.d("result123", String.valueOf(jsonData));
            adp.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
