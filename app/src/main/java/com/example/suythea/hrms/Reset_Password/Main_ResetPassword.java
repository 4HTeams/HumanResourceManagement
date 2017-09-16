package com.example.suythea.hrms.Reset_Password;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import java.util.HashMap;

public class Main_ResetPassword extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    EditText eTxtUsername;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__reset_password);

        MySupporter.runFirstDefault(this);

        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Reset Password");

        eTxtUsername = (EditText)findViewById(R.id.etResetUsername);
        btnReset = (Button)findViewById(R.id.btnResetPass);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });
    }

    void resetPassword(){
        MySupporter.showLoading("Please wait.....");

        HashMap<String, String> map = new HashMap<>();
        map.put("username", eTxtUsername.getText().toString());

        MySupporter.Volley("http://bongnu.myreading.xyz/bongnu/support/reset.php", map, (MySupporter_Interface) this);
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

        Intent intent = new Intent(this, Main_VerifyCode.class);
        intent.putExtra("username", eTxtUsername.getText().toString());
        startActivity(intent);

        finish();
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
