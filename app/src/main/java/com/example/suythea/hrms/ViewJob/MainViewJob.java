package com.example.suythea.hrms.ViewJob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainViewJob extends AppCompatActivity {

    Toolbar toolbar;
    String dataCV;

    TextView txtTitle,txtDesc,txtPosition,txtSalary,txtDeadline,txtExperience,txtconType,txtProvince,txtCarlvl,txtDegree,
    txtProSkill,txtJobCat,txtName,txtEmail,txtEmployeeAmount,txtAddress,txtContact,txtCAbout,txtIdustry,txtCompanyType;
    ImageView profileImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_job);

        dataCV = getIntent().getStringExtra("response");

        txtTitle= (TextView)findViewById(R.id.txtTitle);
        txtDesc= (TextView)findViewById(R.id.txtDescription);
        txtPosition= (TextView)findViewById(R.id.txtPosition);
        txtSalary= (TextView)findViewById(R.id.txtSalary);
        txtDeadline= (TextView)findViewById(R.id.txtDeadline);
        txtExperience= (TextView)findViewById(R.id.txtExperience);
        txtconType= (TextView)findViewById(R.id.txtConType);
        txtProvince= (TextView)findViewById(R.id.txtProvince);
        txtCarlvl= (TextView)findViewById(R.id.txtCarlvl);
        txtDegree= (TextView)findViewById(R.id.txtDegree);
        txtProSkill= (TextView)findViewById(R.id.txtProSkill);
        txtJobCat= (TextView)findViewById(R.id.txtJobCat);
        txtName= (TextView)findViewById(R.id.txtName);
        txtEmail= (TextView)findViewById(R.id.txtEmail);
        txtEmployeeAmount= (TextView)findViewById(R.id.txtEmployee);
        txtAddress= (TextView)findViewById(R.id.txtAddress);
        txtContact= (TextView)findViewById(R.id.txtContact);
        txtCAbout= (TextView)findViewById(R.id.txtCAbout);
        txtIdustry= (TextView)findViewById(R.id.txtIndustry);
        txtCompanyType= (TextView)findViewById(R.id.txtCompanyType);
        profileImage= (ImageView)findViewById(R.id.profileImage);



        setControls();
        setEvents();
        startUp();
        setupData();
    }

    void setupData(){

        try {
            String JobData = dataCV;
            JSONArray jsonArray = new JSONArray(JobData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JobData = jsonObject.getString("job");
            JSONArray jArray = new JSONArray(JobData);
            JSONObject jObject = jArray.getJSONObject(0);
            this.setProfileImage(jObject.getString("uid"));
            txtTitle.setText(jObject.getString("title"));
            txtDesc.setText(jObject.getString("des"));
            txtPosition.setText(jObject.getString("position"));
            txtSalary.setText(jObject.getString("salary"));
            txtDeadline.setText(jObject.getString("deadline"));
            txtExperience.setText(jObject.getString("yearEx"));
            txtconType.setText(jObject.getString("conType"));
            txtProvince.setText(jObject.getString("province"));
            txtCarlvl.setText(jObject.getString("carLvl"));
            txtDegree.setText(jObject.getString("degree"));
            txtProSkill.setText(jObject.getString("proSkill"));
            txtJobCat.setText(jObject.getString("jCate"));

            //Company Information
            String ComData = dataCV;
            ComData = jsonObject.getString("com");
            JSONArray jComArray = new JSONArray(ComData);
            JSONObject jComObject = jComArray.getJSONObject(0);
            txtName.setText(jComObject.getString("name"));
            txtEmail.setText(jComObject.getString("email"));
            txtEmployeeAmount.setText(jComObject.getString("empAmount"));
            txtAddress.setText(jComObject.getString("address"));
            txtContact.setText(jComObject.getString("contact"));
            txtCAbout.setText(jComObject.getString("about"));
            txtIdustry.setText(jComObject.getString("iName"));
            txtCompanyType.setText(jComObject.getString("conType"));

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

        setTitle("View Job");
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

}


