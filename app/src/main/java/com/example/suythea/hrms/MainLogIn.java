package com.example.suythea.hrms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.suythea.hrms.Supporting_Files.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainLogIn extends AppCompatActivity {
    Toolbar toolbar;
    Button btnLogIn;
    EditText etUsername,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        etPassword=(EditText) findViewById(R.id.etLogInPassword) ;
        etUsername=(EditText) findViewById(R.id.etLogInUsername) ;
        toolbar.setTitle("LogIn");

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataVolley();
            }
        });
    }

    public MainLogIn(){
    }

    private void dataVolley(){
        String   password="",
                username="";

        try {
            username = URLEncoder.encode(etUsername.getText().toString(), "utf-8");
            password = URLEncoder.encode(etPassword.getText().toString(), "utf-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://bongNU.khmerlabs.com/bongNU/Account/login.php?appToken=ThEa331RA369RiTH383thY925&username="+username+"&password="+password;
        Log.d("test",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test",response);
                try {
                    JSONArray arrayDB = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8"));

                    int i = 0;
                    while (i < arrayDB.length()){

                        JSONObject jsonObj = arrayDB.getJSONObject(i);

                        //TopListModel topListModel = new TopListModel(jsonObj.getString("ID"),jsonObj.getString("WebAddress"),jsonObj.getString("VisitNumber"),jsonObj.getString("Title"));

                        Log.d("test",jsonObj.getString("status"));
                        if (jsonObj.getString("status")=="success"){
                            Log.d("test",response);
                        }else{
                            jsonObj.getString("message");
                        }

                        i++;
                    }

                } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyVolley.getMyInstance().addToRequestQueue(stringRequest);
    }
}
