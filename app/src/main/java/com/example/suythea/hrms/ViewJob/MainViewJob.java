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

    TextView txtTitle,txtDesc,txtPosition,txtSalary,txtDeadline,txtExperience,txtconType,txtProvince,txtCarlvl,txtDegree;
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
        profileImage= (ImageView)findViewById(R.id.profileImage);

        setControls();
        setEvents();
        startUp();
        setupData();
    }

    void setupData(){

        try {
            JSONArray jsonArray = new JSONArray(dataCV);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            dataCV = jsonObject.getString("job");
            JSONArray jArray = new JSONArray(dataCV);
            JSONObject jObject = jArray.getJSONObject(0);
            this.setProfileImage(jObject.getString("uid"));
            txtTitle.setText("Title: " + jObject.getString("title"));
            txtDesc.setText("Description: \n    " + jObject.getString("des"));
            txtPosition.setText("Position: \n   " + jObject.getString("position"));
            txtSalary.setText("Salary: " + jObject.getString("salary"));
            txtDeadline.setText("Deadline: " + jObject.getString("deadline"));
            txtExperience.setText("Experience: " + jObject.getString("yearEx") + "years");
            txtconType.setText("Type: " + jObject.getString("conType"));
            txtProvince.setText("Location: \n   " + jObject.getString("province"));
            txtCarlvl.setText("CarLvl: " + jObject.getString("carLvl"));
            txtDegree.setText("Degree: \n   " + jObject.getString("degree"));

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
                .load("http://bongnu.myreading.xyz/bong/profile_images/" + url + ".jpg")
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .into(this.profileImage);
    }

}
