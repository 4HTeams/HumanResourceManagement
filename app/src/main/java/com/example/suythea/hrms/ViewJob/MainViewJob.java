package com.example.suythea.hrms.ViewJob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;

public class MainViewJob extends AppCompatActivity {

    Toolbar toolbar;
    String dataCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_job);

        dataCV = getIntent().getStringExtra("response");
        Toast.makeText(this, dataCV, Toast.LENGTH_LONG).show();
        Log.d("result", dataCV);

        setControls();
        setEvents();
        startUp();
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
}
