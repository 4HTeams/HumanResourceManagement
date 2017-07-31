package com.example.suythea.hrms.ViewCV;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainViewCV extends AppCompatActivity {

    Toolbar toolbar;
    String mainData,dataCV,dataAcc,dataExp,dataRef,dataLan,dataSchool;
    TextView txtFirstName,txtLastName,txtGender,txtPhone,txtProvince,txtAbout,txtTitle,txtPostDate;
    TextView txtDesc,txtJobTitle,txtCompanyName,txtStartDate,txtEndDate,txtJcName,txtCtName,txtActivity;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_cv);

        mainData = getIntent().getStringExtra("response");

        Log.d("result",mainData);

        setupView();

        setControls();
        setEvents();
        startUp();
        setupData();
    }

    void setupData(){

        try {
            JSONArray jsonArray = new JSONArray(mainData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            dataCV = jsonObject.getString("CV");
            JSONArray jCVArray = new JSONArray(dataCV);
            JSONObject jCVObject = jCVArray.getJSONObject(0);

            // CV
            this.setProfileImage(jCVObject.getString("uid"));
            txtFirstName.setText("Name: " + jCVObject.getString("fName"));
            txtLastName.setText(jCVObject.getString("lName"));
            txtGender.setText("Gender :" + jCVObject.getString("gender"));
            txtPhone.setText("Phone :" + jCVObject.getString("phone"));
            txtProvince.setText("Province :" + jCVObject.getString("pName"));
            txtAbout.setText("About :" + jCVObject.getString("about"));

            // ACC
            dataAcc = jsonObject.getString("Accc");
            JSONArray jACCArray = new JSONArray(dataAcc);
            JSONObject jACCObject = jACCArray.getJSONObject(0);
            txtTitle.setText("Title: " + jACCObject.getString("title"));
            txtPostDate.setText("Posted Date :" + jACCObject.getString("date"));
            txtDesc.setText("Description :" + jACCObject.getString("des"));

            // EXP
            dataExp = jsonObject.getString("Exp");
            JSONArray jExpArray = new JSONArray(dataExp);
            JSONObject jExpObject = jExpArray.getJSONObject(0);
            txtJobTitle.setText("Job Title :" + jExpObject.getString("jTitle"));
            txtCompanyName.setText("Company Name :" + jExpObject.getString("comName"));
            txtStartDate.setText("Start Date :" + jExpObject.getString("sDate"));
            txtEndDate.setText("End Date :" + jExpObject.getString("eDate"));
            txtJcName.setText("Job Category :" + jExpObject.getString("jcName"));
            txtCtName.setText("Contract Type :" + jExpObject.getString("ctName"));
            txtActivity.setText("Activity :" + jExpObject.getString("activity"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MySupporter.runFirstDefault(this);
    }

    void setControls(){
        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
    }

    void setEvents(){

    }

    void startUp(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        setTitle("View CV");
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

    void setProfileImage(String url) {
        Picasso.with(getBaseContext())
                .load("http://bongnu.myreading.xyz/profile_images/" + url + ".jpg")
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .into(this.profileImage);
    }


    void setupView() {
        txtFirstName = (TextView) findViewById(R.id.txtFistName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtGender= (TextView) findViewById(R.id.txtGender);
        txtPhone= (TextView) findViewById(R.id.txtPhone);
        txtProvince= (TextView) findViewById(R.id.txtProvince);
        txtAbout= (TextView) findViewById(R.id.txtAbout);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtPostDate= (TextView) findViewById(R.id.txtPostDate);
        txtDesc= (TextView) findViewById(R.id.txtDesc);
        txtJobTitle= (TextView) findViewById(R.id.txtJobTitle);
        txtCompanyName= (TextView) findViewById(R.id.txtCompanyName);
        txtStartDate= (TextView) findViewById(R.id.txtStartDate);
        txtEndDate= (TextView) findViewById(R.id.txtEndDate);
        txtJcName= (TextView) findViewById(R.id.txtJcName);
        txtCtName= (TextView) findViewById(R.id.txtCtName);
        txtActivity= (TextView) findViewById(R.id.txtActivity);

        profileImage = (ImageView) findViewById(R.id.profileImage);
    }

}
