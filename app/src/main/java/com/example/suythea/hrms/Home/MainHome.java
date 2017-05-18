package com.example.suythea.hrms.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.List_CV_And_Job_Interface;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewJob.MainViewJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class MainHome extends Fragment implements MySupporter_Interface, List_CV_And_Job_Interface {

    ListView lisView;
    public static ArrayList<ListJobModel> lisData;
    static boolean firstGetData = true;
    public static MainHome context;
    ListJobAdp adp;
    static ProgressBar proBarLoading;
    boolean gettable = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        MySupporter.runFirstDefault(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = this;

        setControls();
        setEvents();
        startUp();

        startGettingData();
    }

    void setControls(){
        lisView = (ListView)getActivity().findViewById(R.id.lisJob);
        proBarLoading = (ProgressBar)getActivity().findViewById(R.id.proBarMainJob);
    }

    void setEvents(){

        final MainHome _c = this;

        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MySupporter.showLoading("Please wait.....");
                MySupporter.Http2("http://bongnu.khmerlabs.com/bongnu/job/get_one_job.php?appToken=ThEa331RA369RiTH383thY925&jid=" + lisData.get(position).getId() + "&cid=" + lisData.get(position).getCid(), new HashMap<String, String>(), getActivity(), (MySupporter_Interface)_c);
            }
        });
    }

    void startUp(){
        lisData = new ArrayList<>();
        adp = new ListJobAdp(getActivity(), R.layout.list_job_item, lisData , this);
        lisView.setAdapter(adp);
    }

    public static void startGettingData(){
        if (firstGetData){
            proBarLoading.setVisibility(View.VISIBLE);
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/job/get_all_jobs.php?appToken=ThEa331RA369RiTH383thY925&offset=0",new HashMap<String, String>(), context);
        }
    }

    @Override
    public void onHttpFinished(String response) {
        try{
            MySupporter.hideLoading();
            Intent intent = new Intent(getActivity(), MainViewJob.class);
            intent.putExtra("response", URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            startActivity(intent);
        }
        catch (Exception e){
            Log.d("result", e.getMessage());
        }
    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(getActivity(), MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVolleyFinished(String response) {
        try {
            firstGetData = false;

            JSONArray jsonArray = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            jsonArray = new JSONArray(new JSONObject(String.valueOf(jsonArray.getJSONObject(0))).getString("data"));
            JSONObject object;

            ListJobModel model;

            for (int i = 0; i < jsonArray.length(); i++){
                object = new JSONObject(String.valueOf(jsonArray.get(i)));
                model = new ListJobModel(object.getString("uid"), object.getString("title"), object.getString("deadline"), object.getString("yearEx"), object.getString("cName"), object.getString("salary"), object.getString("jid"), object.getString("cid"));
                lisData.add(model);
            }

            if (jsonArray.length() < 10){
                gettable = false;
            }

            lisView.setVisibility(View.VISIBLE);
            proBarLoading.setVisibility(View.GONE);
            adp.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolleyError(String message) {

        firstGetData = true;
        proBarLoading.setVisibility(View.GONE);
        Toast.makeText(getActivity(), MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void cameLastIndex() {
        if (gettable){
            proBarLoading.setVisibility(View.VISIBLE);
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/job/get_all_jobs.php?appToken=ThEa331RA369RiTH383thY925&offset=" + lisData.size(),new HashMap<String, String>(), context);
        }
    }
}
