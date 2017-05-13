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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainPostJob extends AppCompatActivity implements MySupporter_Interface, DatePickerDialog.OnDateSetListener {

    JSONArray jCate;
    JSONArray conTypes;
    JSONArray c_lvl;
    JSONArray pro;
    JSONArray degree;

    String order;

    Toolbar toolbar;

    EditText eTxtTitle, eTxtDes, eTxtPosition, eTxtSalary, eTxtDate, eTxtExp, eTxtSkill;
    Spinner spinJobCate, spinCon, spinPro, spinCarLvl, spinDegree;
    Button btnAddDate;

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
    }

    void dataVolleyForFields(){
        MySupporter.Http("http://bongnu.khmerlabs.com/bongnu/get_data_tbl.php?appToken=ThEa331RA369RiTH383thY925&career_lvl=1&province=1&degree=1&job_cate=1&contractType=1", new HashMap<String, String>(), this);
    }

    void loadSpinner(){



        if (order.equals("EDIT")){
            setOldData();
        }
    }

    void setOldData(){

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

    void volleyPost(){

        if (!validateControls().equals("OK")){
            Toast.makeText(this, validateControls(), Toast.LENGTH_LONG).show();
            return;
        }

        MySupporter.showLoading("Please wait.....");

        if (order.equals("POST")){

        }
        else if(order.equals("EDIT")) {

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
    public void onHttpError(String message) {
        MySupporter.hideLoading();

        if (jCate.length() < 1 || conTypes.length() < 1 || c_lvl.length() < 1 || pro.length() < 1 || degree.length() < 1){
            Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onVolleyFinished(String response) {

    }

    @Override
    public void onVolleyError(String message) {

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
