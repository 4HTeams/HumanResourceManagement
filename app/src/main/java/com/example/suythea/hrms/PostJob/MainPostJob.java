package com.example.suythea.hrms.PostJob;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.ViewOwn_Job_CV_Interface;
import com.example.suythea.hrms.PostCV.ListPostCVModel;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewOwnCV.MainViewOwnCV;
import com.example.suythea.hrms.ViewOwnJob.MainViewOwnJob;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainPostJob extends AppCompatActivity implements MySupporter_Interface, DatePickerDialog.OnDateSetListener {

    JSONArray jCate;
    JSONArray conTypes;
    JSONArray c_lvl;
    JSONArray pro;
    JSONArray degree;

    String order, oldJID;

    Toolbar toolbar;

    ImageView img;

    ScrollView scrollView;

    EditText eTxtTitle, eTxtDes, eTxtPosition, eTxtSalary, eTxtDate, eTxtExp, eTxtSkill;
    Spinner spinJobCate, spinCon, spinPro, spinCarLvl, spinDegree;
    Button btnAddDate;

    ArrayList<String> provinces, jobCate, con, carLvl, de;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_job);

        MySupporter.runFirstDefault(this);

        setControls();
        setEvents();
        startUp();
    }

    void setControls(){
        toolbar = (Toolbar)findViewById(R.id.toolBarNoSearch);
        eTxtTitle = (EditText)findViewById(R.id.eTxtTitlePostJob);
        eTxtDes = (EditText)findViewById(R.id.eTxtDesPostJob);
        eTxtPosition = (EditText)findViewById(R.id.eTxtJobPositionPostJob);
        eTxtSalary = (EditText)findViewById(R.id.eTxtSalaryPostJob);
        eTxtDate = (EditText)findViewById(R.id.eTxtDatePostJob);
        eTxtExp = (EditText)findViewById(R.id.eTxtExpPostJob);
        eTxtSkill = (EditText)findViewById(R.id.eTxtSkillsPostJob);
        btnAddDate = (Button)findViewById(R.id.btnAddDatePostJob);
        spinJobCate = (Spinner)findViewById(R.id.spinJobCatePostJob);
        spinCon = (Spinner)findViewById(R.id.spinConTypePostJob);
        spinPro = (Spinner)findViewById(R.id.spinProPostJob);
        spinCarLvl = (Spinner)findViewById(R.id.spinCarLvlPostJob);
        spinDegree = (Spinner)findViewById(R.id.spinDegreePostJob);
        img = (ImageView)findViewById(R.id.imgProfileMainPostJob);
        scrollView = (ScrollView)findViewById(R.id.scrollMainPostJob);
    }

    void setEvents(){
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    void showDatePicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datepickerdialog = new DatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT, this,year,month,day);
        datepickerdialog.show();
    }

    void startUp(){

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        order = getIntent().getStringExtra("order");

        this.jCate = new JSONArray();
        this.conTypes = new JSONArray();
        this.c_lvl = new JSONArray();
        this.pro = new JSONArray();
        this.degree = new JSONArray();


        MySqlite sqlite = new MySqlite(this);

        String jCate = sqlite.getDataFromjsonField(MySqlite.fields.get(7), "_all_db");
        String conTypes = sqlite.getDataFromjsonField(MySqlite.fields.get(6), "_all_db");
        String c_lvl = sqlite.getDataFromjsonField(MySqlite.fields.get(8), "_all_db");
        String pro = sqlite.getDataFromjsonField(MySqlite.fields.get(3), "_all_db");
        String degree = sqlite.getDataFromjsonField(MySqlite.fields.get(5), "_all_db");

        if (jCate.equals("") || conTypes.equals("") || c_lvl.equals("") || pro.equals("") || degree.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            try {

                this.jCate = new JSONArray(jCate);
                this.conTypes = new JSONArray(conTypes);
                this.c_lvl = new JSONArray(c_lvl);
                this.pro = new JSONArray(pro);
                this.degree = new JSONArray(degree);

                loadSpinner();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        dataVolleyForFields();

        Picasso.with(this)
                .load("http://bongnu.khmerlabs.com/profile_images/" + new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"id") + ".jpg")
                .placeholder(this.getResources().getIdentifier("no_profile","mipmap",this.getPackageName()))
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    void dataVolleyForFields(){
        MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php?appToken=ThEa331RA369RiTH383thY925&career_lvl=1&province=1&degree=1&job_cate=1&contractType=1", new HashMap<String, String>(), this);
    }

    void loadSpinner(){

        scrollView.setVisibility(View.VISIBLE);

        try {

            int i = 0;

            provinces = new ArrayList<>();
            jobCate = new ArrayList<>();
            con = new ArrayList<>();
            carLvl = new ArrayList<>();
            de = new ArrayList<>();

            while (i < pro.length()) {

                provinces.add(pro.getJSONObject(i).getString("proName"));
                i++;
            }
            i = 0;

            while (i < c_lvl.length()) {

                carLvl.add(c_lvl.getJSONObject(i).getString("clName"));
                i++;
            }
            i = 0;

            while (i < jCate.length()) {

                jobCate.add(jCate.getJSONObject(i).getString("jcName"));
                i++;
            }
            i = 0;

            while (i < conTypes.length()) {

                con.add(conTypes.getJSONObject(i).getString("ctName"));
                i++;
            }
            i = 0;

            while (i < degree.length()) {

                de.add(degree.getJSONObject(i).getString("dName"));
                i++;
            }
            i = 0;

            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinces);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinPro.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, de);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinDegree.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carLvl);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCarLvl.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobCate);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinJobCate.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, con);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCon.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setOldData();
    }

    void setOldData(){

        try {

            if (!order.equals("EDIT")){
                return;
            }

            JSONObject data = new JSONObject(URLDecoder.decode(URLEncoder.encode(MainViewOwnJob.oldJobData, "iso8859-1"),"UTF-8"));

            oldJID = data.getString("jid");

            eTxtDate.setText("Deadline : " + data.getString("application_deadline"));
            eTxtTitle.setText(data.getString("job_title"));
            eTxtSkill.setText(data.getString("professional_skill"));
            eTxtExp.setText(data.getString("year_experience"));
            eTxtSalary.setText(data.getString("salary"));
            eTxtDes.setText(data.getString("job_description"));
            eTxtPosition.setText(data.getString("position_requirement"));

            for (int i=0; i<pro.length(); i++){

                if (pro.getJSONObject(i).getString("id").equals(data.getString("province"))){
                    spinPro.setSelection(i);
                    break;
                }
            }

            for (int i=0; i<conTypes.length(); i++){

                if (conTypes.getJSONObject(i).getString("id").equals(data.getString("contract_type"))){
                    spinCon.setSelection(i);
                    break;
                }
            }

            for (int i=0; i<jCate.length(); i++){

                if (jCate.getJSONObject(i).getString("id").equals(data.getString("job_category"))){
                    spinJobCate.setSelection(i);
                    break;
                }
            }

            for (int i=0; i<c_lvl.length(); i++){

                if (c_lvl.getJSONObject(i).getString("id").equals(data.getString("career_level"))){
                    spinCarLvl.setSelection(i);
                    break;
                }
            }

            for (int i=0; i<degree.length(); i++){

                if (degree.getJSONObject(i).getString("id").equals(data.getString("degree"))){
                    spinDegree.setSelection(i);
                    break;
                }
            }

            Log.d("result", String.valueOf(data));

        } catch (JSONException e) {e.printStackTrace();} catch (UnsupportedEncodingException e) {e.printStackTrace();}
    }

    void volleyPost(){

        MySqlite sqlite = new MySqlite(this);
        HashMap<String, String> params = new HashMap<>();

        if (!validateControls().equals("OK")){
            Toast.makeText(this, validateControls(), Toast.LENGTH_LONG).show();
            return;
        }

        MySupporter.showLoading("Please wait.....");

        try {
            params.put("appToken", "ThEa331RA369RiTH383thY925");
            params.put("id", sqlite.getDataFromjsonField(MySqlite.fields.get(0), "id"));
            params.put("cid", new JSONArray(sqlite.getDataFromjsonField(MySqlite.fields.get(0), "companyInfo")).getJSONObject(0).getString("cID"));
            params.put("title", eTxtTitle.getText().toString());
            params.put("des", eTxtDes.getText().toString());
            params.put("p_req", eTxtPosition.getText().toString());
            params.put("job_cate", jCate.getJSONObject(spinJobCate.getSelectedItemPosition()).getString("id"));
            params.put("con_type", conTypes.getJSONObject(spinCon.getSelectedItemPosition()).getString("id"));
            params.put("salary", eTxtSalary.getText().toString());
            params.put("pro", pro.getJSONObject(spinPro.getSelectedItemPosition()).getString("id"));
            params.put("deadline", eTxtDate.getText().toString().substring(eTxtDate.getText().toString().indexOf(':') + 2, eTxtDate.getText().toString().length()));
            params.put("c_lvl", c_lvl.getJSONObject(spinCarLvl.getSelectedItemPosition()).getString("id"));
            params.put("degree", degree.getJSONObject(spinDegree.getSelectedItemPosition()).getString("id"));
            params.put("yearEx", eTxtExp.getText().toString());
            params.put("proSkill", eTxtSkill.getText().toString());
            params.put("conPassword", sqlite.getDataFromjsonField(MySqlite.fields.get(0), "password"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (order.equals("POST")){
            params.put("order", "POST");
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/job/post_job.php", params, this);
        }
        else if(order.equals("EDIT")) {
            params.put("order", "EDIT");
            params.put("jid", oldJID);
            MySupporter.Volley("http://bongnu.khmerlabs.com/bongnu/job/post_job.php", params, this);
        }
    }

    String validateControls(){

        if (eTxtTitle.getText().toString().equals("") || eTxtExp.getText().toString().equals("") || eTxtSalary.getText().toString().equals("") || eTxtPosition.getText().toString().equals("") || eTxtDes.getText().toString().equals("") || eTxtSkill.getText().toString().equals("")){
            return "Fill all controls required !";
        }
        else if (eTxtDate.getText().toString().substring(eTxtDate.getText().toString().indexOf(':') + 2, eTxtDate.getText().toString().length()).equals("NULL")){
            return "Fill deadline required !";
        }

        return "OK";
    }

    @Override
    public void onHttpFinished(String response) {
        MySupporter.hideLoading();

        String data = "", pro = "", c_lvl = "", degree = "", contractType = "", job_cate = "";

        try {
            data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");

            pro = new JSONArray(data).getJSONObject(0).getString("provinces");
            c_lvl = new JSONArray(data).getJSONObject(0).getString("c_lvl");
            degree = new JSONArray(data).getJSONObject(0).getString("degree");
            contractType = new JSONArray(data).getJSONObject(0).getString("contractType");
            job_cate = new JSONArray(data).getJSONObject(0).getString("job_cate");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (this.jCate.length() < 1 || this.conTypes.length() < 1 || this.c_lvl.length() < 1 || this.pro.length() < 1 || this.degree.length() < 1) {

            try {

                this.jCate = new JSONArray(job_cate);
                this.conTypes = new JSONArray(contractType);
                this.c_lvl = new JSONArray(c_lvl);
                this.pro = new JSONArray(pro);
                this.degree = new JSONArray(degree);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            loadSpinner();
        }

        MySqlite sqlite = new MySqlite(this);
        sqlite.insertJsonDB(MySqlite.fields.get(7), job_cate);
        sqlite.insertJsonDB(MySqlite.fields.get(6), contractType);
        sqlite.insertJsonDB(MySqlite.fields.get(8), c_lvl);
        sqlite.insertJsonDB(MySqlite.fields.get(3), pro);
        sqlite.insertJsonDB(MySqlite.fields.get(5), degree);

    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();

        if (jCate.length() < 1 || conTypes.length() < 1 || c_lvl.length() < 1 || pro.length() < 1 || degree.length() < 1){
            Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onVolleyFinished(String response) {

        MySupporter.hideLoading();

        try {
            JSONArray json = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8"));

            if (String.valueOf(json.getJSONObject(0).getString("status")).equals("Success")){

                if (order.equals("EDIT")){
                    prepareNewJsonRow(response);
                }

                finish();
            }
            else {
                Toast.makeText(this, String.valueOf(json.getJSONObject(0).getString("Message")), Toast.LENGTH_LONG).show();
            }

        } catch (UnsupportedEncodingException e) {e.printStackTrace();} catch (JSONException e) {e.printStackTrace();}

    }

    void prepareNewJsonRow (String response) throws UnsupportedEncodingException, JSONException {

        ViewOwn_Job_CV_Interface _interface = (ViewOwn_Job_CV_Interface) MainViewOwnJob.context;
        JSONObject data = new JSONObject(URLDecoder.decode(URLEncoder.encode(MainViewOwnJob.oldJobData, "iso8859-1"),"UTF-8"));

        JSONObject object = new JSONObject();

        object.put("cid", data.getString("cid"));
        object.put("uid", data.getString("uid"));
        object.put("job_title", eTxtTitle.getText().toString());
        object.put("job_description", eTxtDes.getText().toString());
        object.put("position_requirement", eTxtPosition.getText().toString());
        object.put("job_category", jCate.getJSONObject(spinJobCate.getSelectedItemPosition()).getString("id"));
        object.put("contract_type", conTypes.getJSONObject(spinCon.getSelectedItemPosition()).getString("id"));
        object.put("salary", eTxtSalary.getText().toString());
        object.put("province", pro.getJSONObject(spinPro.getSelectedItemPosition()).getString("id"));
        object.put("application_deadline", eTxtDate.getText().toString().substring(eTxtDate.getText().toString().indexOf(':') + 2, eTxtDate.getText().toString().length()));
        object.put("career_level", c_lvl.getJSONObject(spinCarLvl.getSelectedItemPosition()).getString("id"));
        object.put("degree", degree.getJSONObject(spinDegree.getSelectedItemPosition()).getString("id"));
        object.put("year_experience", eTxtExp.getText().toString());
        object.put("professional_skill", eTxtSkill.getText().toString());
        object.put("jid", data.getString("jid"));

        _interface.reloadChangedData(object);
    }

    @Override
    public void onVolleyError(String message) {
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
        MySupporter.hideLoading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case android.R.id.home :
                finish();
                break;

            case R.id.icSend :
                volleyPost();
                break;
        }
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = format.format(calendar.getTime());

        eTxtDate.setText("Deadline : " + strDate);

    }
}
