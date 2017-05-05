package com.example.suythea.hrms.ViewCV;

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
import com.example.suythea.hrms.Interfaces.ViewCV_Interface;
import com.example.suythea.hrms.PostCV.MainPostCV;
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

public class MainViewCV extends AppCompatActivity implements MySupporter_Interface, ViewCV_Interface{

    ListViewCVAdp adp;
    ListView lisViewCV;
    Toolbar toolbar;
    JSONArray jsonData;
    String httpTag;
    int deletingID;
    int selectedID;
    ArrayList<ListViewCVModel> listCVModels;
    public static String oldCVData;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_cv);

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
        lisViewCV = (ListView)findViewById(R.id.lisViewCV);
    }

    void setEvents(){
        lisViewCV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            String cvID = jsonData.getJSONObject(position).getString("id");

                            switch(item.getItemId()){

                            case R.id.menu_edit_post_cv:
                                httpGetOneCV(cvID, position);
                                break;

                            case R.id.menu_delete_post_cv:
                                deleteCV(cvID, position);
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

        setTitle("View CV");

        listCVModels = new ArrayList<>();
        adp = new ListViewCVAdp(this, R.layout.list_own_cv_item, listCVModels);
        lisViewCV.setAdapter(adp);

        volleyGetOwnCV();
    }

    void deleteCV (String id, int selectedID){
        deletingID = selectedID;
        httpTag = "DELETE";
        MySupporter.showLoading("Please Wait.....");

        HashMap<String, String> params = new HashMap<>();
        params.put("appToken", "ThEa331RA369RiTH383thY925");
        params.put("emp_id", id);
        params.put("id", new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"id"));
        params.put("conPassword", new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"password"));

        MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/postcv/delete_cv.php", params, this);
    }

    void httpGetOneCV(String id, int selectedID){
        this.selectedID = selectedID;
        httpTag = "ONECV";
        MySupporter.showLoading("Please Wait.....");
        MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/postcv/get_one_cv.php?appToken=ThEa331RA369RiTH383thY925&id=" + id, new HashMap<String, String>(), this);
    }

    void volleyGetOwnCV(){
        MySupporter.showLoading("Please Wait.....");
        String id = new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"id");
        MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/postcv/get_own_cv.php?appToken=ThEa331RA369RiTH383thY925&id=" + id,new HashMap<String, String>(),this);
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

            if (httpTag.equals("DELETE")){


                if (jsonObj.getString("status").equals("Success")){
                    Toast.makeText(this, "Deleted Successfully.", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        jsonData.remove(deletingID);
                    }

                    listCVModels.remove(deletingID);
                    adp.notifyDataSetChanged();
                }

            }
            else if (httpTag.equals("ONECV")){

                if (jsonObj.getString("status").equals("Success")){
                    oldCVData = response;
                    Intent i = new Intent(this, MainPostCV.class);
                    i.putExtra("order", "EDIT");
                    startActivity(i);
                }
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

            listCVModels = new ArrayList<>();
            jsonData = new JSONArray(new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8")).getJSONObject(0).getString("data"));

            Log.d("result", String.valueOf(jsonData));

            for(int i = 0; i< jsonData.length(); i++){
                ListViewCVModel cvModel = new ListViewCVModel(jsonData.getJSONObject(i).getString("fName"), jsonData.getJSONObject(i).getString("lName"), jsonData.getJSONObject(i).getString("posted_date"), jsonData.getJSONObject(i).getString("title"));
                listCVModels.add(cvModel);
            }

            adp = new ListViewCVAdp(this, R.layout.list_own_cv_item, listCVModels);
            lisViewCV.setAdapter(adp);

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
    public void regetOwnCV(Object object) {
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
            ListViewCVModel cvModel = new ListViewCVModel(jsonData.getJSONObject(selectedID).getString("fName"), jsonData.getJSONObject(selectedID).getString("lName"), jsonData.getJSONObject(selectedID).getString("posted_date"), jsonData.getJSONObject(selectedID).getString("title"));
            listCVModels.set(selectedID, cvModel);

            Log.d("result123", String.valueOf(jsonData));
            adp.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
