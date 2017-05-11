package com.example.suythea.hrms.CV;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.ListCV_Interface;
import com.example.suythea.hrms.Interfaces.Main_Interface;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.SearchCV.MainSearchCV;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewCV.MainViewCV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainCV extends Fragment implements MySupporter_Interface, ListCV_Interface{

    ListView lisView;
    public static ArrayList<ListCVModel> lisData;
    static boolean firstGetData = true;
    public static MainCV context;
    ListCVAdp adp;
    ProgressBar proBarLoading;
    ProgressBar proBarMore;
    boolean gettable = true;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_cv, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = this;

        setControls();
        setEvents();
        startUp();
    }

    @Override
    public void onStart() {
        super.onStart();

        MySupporter.runFirstDefault(getActivity());

    }

    void setControls(){
        lisView = (ListView)getActivity().findViewById(R.id.lisCV);
        proBarLoading = (ProgressBar)getActivity().findViewById(R.id.proBarMainCV);
        proBarMore = (ProgressBar)getActivity().findViewById(R.id.proBarMoreMainCV);
        searchView = (SearchView)getActivity().findViewById(R.id.mySearch);
    }

    void setEvents(){

        final MainCV _c = this;

        lisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MySupporter.showLoading("Please wait.....");
                MySupporter.Http2("http://bongnu.khmerlabs.com/bongnu/cv/get_one_cv.php?appToken=ThEa331RA369RiTH383thY925&id=" + lisData.get(position).getId(), new HashMap<String, String>(), getActivity(), (MySupporter_Interface)_c);
            }
        });
    }

    void startUp(){
        lisData = new ArrayList<>();
        adp = new ListCVAdp(getActivity(), R.layout.list_cv_item, lisData , this);
        lisView.setAdapter(adp);
    }

    public static void startGettingData(){
        if (firstGetData){
            firstGetData = false;
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/cv/get_all_cv.php?appToken=ThEa331RA369RiTH383thY925&offset=0",new HashMap<String, String>(), context);
        }
    }

    @Override
    public void onHttpFinished(String response) {
        try{
            MySupporter.hideLoading();
            Intent intent = new Intent(getActivity(), MainViewCV.class);
            intent.putExtra("response", response);
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

            JSONArray jsonArray = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            jsonArray = new JSONArray(new JSONObject(String.valueOf(jsonArray.getJSONObject(0))).getString("data"));
            JSONObject object;

            ListCVModel model;

            for (int i = 0; i < jsonArray.length(); i++){
                object = new JSONObject(String.valueOf(jsonArray.get(i)));
                model = new ListCVModel(object.getString("fName"), object.getString("lName"), object.getString("posted_date"), object.getString("title"), object.getString("id"), object.getString("uid"));
                lisData.add(model);
            }

            lisView.setVisibility(View.VISIBLE);
            proBarLoading.setVisibility(View.GONE);
            adp.notifyDataSetChanged();

            if (jsonArray.length() < 10){
                gettable = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolleyError(String message) {

        firstGetData = true;
        proBarMore.setVisibility(View.GONE);
        Toast.makeText(getActivity(), MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void cameLastIndex() {
        if (gettable){
            proBarLoading.setVisibility(View.VISIBLE);
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/cv/get_all_cv.php?appToken=ThEa331RA369RiTH383thY925&offset=" + lisData.size(),new HashMap<String, String>(), context);
        }
    }
}
