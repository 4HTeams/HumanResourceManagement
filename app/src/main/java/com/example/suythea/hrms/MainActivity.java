package com.example.suythea.hrms;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.suythea.hrms.Home.MainHome;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Supporting_Files.Main_Interface;

public class MainActivity extends AppCompatActivity implements Main_Interface {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControls();
        setEvents();
        startUp();
    }

    void setControls() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    void setEvents(){

    }

    void startUp(){
        setTitle("Welcome To HRMS !");
        setSupportActionBar(toolbar);

        // To Add SubFragments
        viewPagerAdapter.addFragmentAndTitle(new MainHome(this), "Home");
        viewPagerAdapter.addFragmentAndTitle(new MainSetting(this), "Setting");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void runListener(String stringTest) {
        Toast.makeText(this,stringTest,Toast.LENGTH_SHORT).show();
    }
}
