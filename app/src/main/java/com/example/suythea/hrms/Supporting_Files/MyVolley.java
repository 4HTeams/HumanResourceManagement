package com.example.suythea.hrms.Supporting_Files;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by lolzzlolzz on 7/18/16.
 */
public class MyVolley extends Application {

    static MyVolley myInstance;
    static RequestQueue myRequestQ;
    static final String TAG = "DEFAULT";
    static String OLDTAG = "DEFAULT";


    @Override
    public void onCreate() {
        super.onCreate();

        myInstance = this;
    }

    public static synchronized MyVolley getMyInstance(){
        return myInstance;
    }

    public RequestQueue getMyRequestQ(){
        if (myRequestQ == null){
            myRequestQ = Volley.newRequestQueue(this.getApplicationContext());
        }
        else{
            cancelPendingRequests(OLDTAG);
        }
        OLDTAG = TAG;
        return myRequestQ;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag)? TAG : tag);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getMyRequestQ().add(request);
    }

    public static void cancelPendingRequests(Object tag){
        if (myRequestQ != null){
            myRequestQ.cancelAll(tag);
        }
    }

    public static void cancelOldPandingRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        MyVolley.getMyInstance().addToRequestQueue(stringRequest);
    }
}
