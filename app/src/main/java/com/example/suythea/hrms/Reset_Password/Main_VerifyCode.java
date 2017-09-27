package com.example.suythea.hrms.Reset_Password;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

public class Main_VerifyCode extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    String username;
    EditText eTxtCode;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__verify_code);


        MySupporter.runFirstDefault(this);

        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Confirm Code");
        username = getIntent().getExtras().getString("username");

        eTxtCode = (EditText)findViewById(R.id.etVerifyCode);
        btnVerify = (Button)findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
                verifyCode();
            }
        });
    }

    void verifyCode() {
        MySupporter.showLoading("Please wait.....");

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("verifyCode", eTxtCode.getText().toString());

        MySupporter.Volley("http://bongnu.myreading.xyz/bongnu/support/verify.php", map, (MySupporter_Interface) this);
    }

    @Override
    public void onHttpFinished(String response) {

    }

    @Override
    public void onHttpError(String message) {

    }

    @Override
    public void onVolleyFinished(String response) {

        MySupporter.hideLoading();

        try {
            String data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");
            String status = new JSONArray(data).getJSONObject(0).getString("status");

            if (status.equals("Success")){

                Intent intent = new Intent(this, Main_New_Password.class);
                intent.putExtra("username", username);
                startActivity(intent);

                finish();
            } else {
                Snackbar.make(toolbar, "Your Verify Code does not match !", Snackbar.LENGTH_LONG).show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVolleyError(String message) {

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
