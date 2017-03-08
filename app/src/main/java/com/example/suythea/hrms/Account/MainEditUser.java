package com.example.suythea.hrms.Account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.Supporting_Files.MyVolley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainEditUser extends AppCompatActivity implements MySupporter_Interface{

    Toolbar toolbar;
    CheckBox ckbPassword;
    LinearLayout linPassword;
    EditText eTxtUsername, eTxtEmail, eTxtOldPass, eTxtNewPass, eTxtConPass;
    JSONObject jsonData;
    TextView txtBrowse, txtRemove;
    ImageView imgProfile;
    String imgState;
    Bitmap currentImg;
    Map<String, Boolean> changeOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edit_user);

        setControls();
        setEvents();
        startUp();
    }

    void setControls (){
        toolbar = (Toolbar) findViewById(R.id.toolBarNoSearch);
        ckbPassword = (CheckBox) findViewById(R.id.ckbPasswordEditUser);
        linPassword = (LinearLayout) findViewById(R.id.linPasswordEditUser);

        eTxtUsername = (EditText) findViewById(R.id.eTxtUsernameEditUser);
        eTxtEmail = (EditText) findViewById(R.id.eTxtEmailEditUser);
        eTxtOldPass = (EditText) findViewById(R.id.eTxtOldPasswordEditUser);
        eTxtNewPass = (EditText) findViewById(R.id.eTxtNewPasswordEditUser);
        eTxtConPass = (EditText) findViewById(R.id.eTxtConfirmPassEditUser);

        txtBrowse = (TextView) findViewById(R.id.txtBrowseEditUser);
        txtRemove = (TextView) findViewById(R.id.txtRemoveEditUser);

        imgProfile = (ImageView) findViewById(R.id.imgProfileEditUser);
    }

    void setEvents (){
        ckbPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckbPassword.isChecked()){
                    linPassword.setVisibility(View.VISIBLE);
                    return;
                }
                linPassword.setVisibility(View.GONE);
            }
        });

        txtBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 100);
            }
        });

        txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRemove.setTextColor(Color.parseColor("gray"));
                imgProfile.setImageResource(getBaseContext().getResources().getIdentifier("no_profile","mipmap",getBaseContext().getPackageName()));
                imgState = "Remove";
            }
        });

    }

    void startUp (){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Change Info");

        imgState = "";

        getSqlDb();
        setOldValues();
    }

    void getSqlDb (){

        MySqlite sqlite = new MySqlite(this);

        try {
            jsonData = new JSONArray(sqlite.getDataFromjsonField(MySqlite.tables.get(0),"_all_db")).getJSONObject(0);
            Log.d("myResult", String.valueOf(jsonData));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void setOldValues (){

        try {
            eTxtUsername.setText(jsonData.getString("username"));
            eTxtEmail.setText(jsonData.getString("email"));

            if (jsonData.getString("profile_url").equals("0")){
                txtRemove.setTextColor(Color.parseColor("gray"));
            }
            else {
                Picasso.with(getBaseContext())
                        .load("http://bongnu.khmerlabs.com/profile_images/" + jsonData.getString("id") + ".jpg")
                        .placeholder(getBaseContext().getResources().getIdentifier("no_profile","mipmap",getBaseContext().getPackageName()))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(imgProfile);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 100 && resultCode == RESULT_OK){
            try {

                txtRemove.setTextColor(Color.parseColor("#387ef5"));

                final Uri imageUri = intent.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                currentImg = BitmapFactory.decodeStream(imageStream,null,options);
                imgProfile.setImageBitmap(currentImg);

                imgState = "Yes";

            } catch (FileNotFoundException e) {
                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.icSave :

                String checkResult = check_ChangeOrNot_SendOrNot();

                if (checkResult.equals("Go")){
                    dataVolley();
                }
                else if (checkResult.equals("Nothing")){
                    Snackbar.make(toolbar, "Nothing Changed !", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(toolbar, checkResult, Snackbar.LENGTH_LONG).show();
                }

                break;
            case android.R.id.home :
                finish();
                break;
        }

        return true;
    }


    private void dataVolley(){

        Map<String, String> params = new HashMap<>();

        params.put("appToken", "ThEa331RA369RiTH383thY925");

        try {

            params.put("id", jsonData.getString("id"));

            if (changeOrNot.containsKey("email")){
                params.put("email", eTxtEmail.getText().toString());
            }
            if (changeOrNot.containsKey("password")){
                params.put("oldPassword", jsonData.getString("password"));
                params.put("newPassword", eTxtNewPass.getText().toString());
            }
            if (changeOrNot.containsKey("img")){
                if (imgState.equals("Yes")){
                    params.put("img", MySupporter.encodeBase64(currentImg));
                }
                else if (imgState.equals("Remove")){
                    params.put("img", "remove");
                }
            }
        }
        catch (JSONException e) {e.printStackTrace();}

        MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/account/edit_user.php", params, this);

    }

    String check_ChangeOrNot_SendOrNot (){

        changeOrNot = new HashMap<>();
        Map<String, Boolean> changing = new HashMap<>();

        try {

            if (!eTxtEmail.getText().toString().equals(jsonData.getString("email"))){
                changeOrNot.put("email", true);
            }
            else{
                changing.put("email", true);
            }

            if (imgState.equals("Yes") || imgState.equals("Remove")){
                changeOrNot.put("img", true);
            }
            else {
                changing.put("img", true);
            }

            if (ckbPassword.isChecked()){
                if (jsonData.getString("password").equals(eTxtOldPass.getText().toString())){
                    changeOrNot.put("password", true);
                }
            }
            else {
                changing.put("password", true);
            }

        } catch (JSONException e) {e.printStackTrace();}

        if (changing.containsKey("email") && changing.containsKey("img") && changing.containsKey("password")){
            return "Nothing";
        }

        return "Go";
    }

    void afterDB (){

        try {
            if (changeOrNot.containsKey("email")){
                jsonData.put("email", eTxtEmail.getText().toString());
            }
            if (changeOrNot.containsKey("password")){
                jsonData.put("password", eTxtNewPass.getText().toString());
            }
            if (changeOrNot.containsKey("img")){
                if (imgState.equals("Yes")){
                    jsonData.put("profile_url", "1");
                }
                else if (imgState.equals("Remove")){
                    jsonData.put("profile_url", "0");
                }
            }
        }
        catch (JSONException e) {e.printStackTrace();}

        MySqlite sqlite = new MySqlite(this);
        sqlite.insertUser("[" + String.valueOf(jsonData) + "]");
    }

    @Override
    public void onFinished(String response) {

        try {
            String status = new JSONArray(response).getJSONObject(0).getString("status");

            if (status.equals("Success")){
                afterDB();
                finish();
            }
            else if (status.equals("Error")){
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
            else if (status.equals("SuccessWithError")){
                Toast.makeText(getBaseContext(), "SuccessWithError", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
