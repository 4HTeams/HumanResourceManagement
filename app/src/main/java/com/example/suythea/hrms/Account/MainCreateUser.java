package com.example.suythea.hrms.Account;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.Setting_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.Supporting_Files.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainCreateUser extends AppCompatActivity implements MySupporter_Interface {

    EditText etUsername, etPassword, etConfirmPassword, etEmail;
    Button btnCreate;
    Toolbar toolbar;
    Setting_Interface setting_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_main_create_user);

        MySupporter.runFirstDefault(this);

        try {
            toolbar = (Toolbar) findViewById(R.id.toolBarNoSearch);
        }catch (Exception e){
            Snackbar.make(toolbar,e.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
        }

        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etConfirmPassword= (EditText) findViewById(R.id.etConfirmPassword);
        etEmail= (EditText) findViewById(R.id.etEmail);
        btnCreate=(Button) findViewById(R.id.btnCreate);
        setting_interface = MainSetting.context;

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    Toast.makeText(getBaseContext(),"Password not match",Toast.LENGTH_LONG).show();
                }
                else {
                    MySupporter.showLoading("Please wait.....");
                    dataVolley();
                }
            }
        });

        toolbar.setTitle("Create Account");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp); // your drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }

    private void dataVolley(){

        String  username="",
                password="",
                email="";

        try {

            username = URLEncoder.encode(etUsername.getText().toString(), "utf-8");
            password = URLEncoder.encode(etPassword.getText().toString(), "utf-8");
            email = URLEncoder.encode(etEmail.getText().toString(), "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        params.put("appToken","ThEa331RA369RiTH383thY925");
        params.put("username",username);
        params.put("password",password);
        params.put("email",email);

        MySupporter.Volley("http://bongNU.khmerlabs.com/bongNU/Account/create.php", params, this);
    }

    void alterJson (String id){

        MySqlite sqlite = new MySqlite(this);

        JSONObject object = new JSONObject();
        try {
            object.put("id",id);
            object.put("username",etUsername.getText().toString());
            object.put("email",etEmail.getText().toString());
            object.put("password",etPassword.getText().toString());
            object.put("type","1");
            object.put("approval","1");
            object.put("profile_url","0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sqlite.insertUser("[" + String.valueOf(object) + "]");

    }

    @Override
    public void onFinished(String response) {

        try {

            String decodeRes = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");
            JSONArray arrayDB = new JSONArray(decodeRes);

            while (true){

                JSONObject jsonObj = arrayDB.getJSONObject(0);

                if (jsonObj.getString("status").equals("Success")){

                    alterJson(jsonObj.getString("id"));
                    setting_interface.changeToFragment("SEEKER_PROFILE");
                    finish();

                }else{
                    Snackbar.make(toolbar, jsonObj.getString("message") , Snackbar.LENGTH_LONG).show();
                }

                break;
            }

        } catch(JSONException e){e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }

        MySupporter.hideLoading();

    }

    @Override
    public void onError(String message) {
        MySupporter.hideLoading();
        MySupporter.checkError();
    }
}
