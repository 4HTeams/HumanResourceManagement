package com.example.suythea.hrms.Supporting_Files;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.MainActivity;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MySupporter {

    static MySupporter_Interface mySupporter_interface;

    public static String encodeBase64 (Bitmap bitmap){

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bao);
        byte [] ba = bao.toByteArray();

        return Base64.encodeToString(ba,Base64.DEFAULT);
    }

    public static void Volley (String url, final Map<String, String> params, MySupporter_Interface _mySupporter_interface){

        MyVolley.cancelOldPandingRequest();
        mySupporter_interface = _mySupporter_interface;

        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mySupporter_interface.onFinished(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mySupporter_interface.onError(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        MyVolley.getMyInstance().addToRequestQueue(stringRequest);
    }

    public static void Http (String url, Map<String,String> params, Context context){

        MyVolley.cancelOldPandingRequest();
        mySupporter_interface = (MySupporter_Interface) context;

        MyHttp myTask = new MyHttp(context, (HashMap<String, String>) params, new AsyncResponse() {
            @Override
            public void processFinish(String response) {
                mySupporter_interface.onFinished(response);
            }
        });

        myTask.execute(url);
        myTask.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                mySupporter_interface.onError(e.getMessage());
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                mySupporter_interface.onError(e.getMessage());
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                mySupporter_interface.onError(e.getMessage());
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                mySupporter_interface.onError(e.getMessage());
            }
        });
    }

    public static void showSnackBar (String message){
        Snackbar.make(MainActivity.toolbar, message, Snackbar.LENGTH_LONG).show();
    }

}
