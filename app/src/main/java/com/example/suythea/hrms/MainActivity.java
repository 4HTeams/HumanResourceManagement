package com.example.suythea.hrms;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.suythea.hrms.CV.MainCV;
import com.example.suythea.hrms.Home.MainHome;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Interfaces.Main_Interface;

public class MainActivity extends AppCompatActivity implements Main_Interface {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public static Context context;

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
        setTitle("ស្វាគមន៍មកកាន់ បងន័រតុន");
        setSupportActionBar(toolbar);

        viewPagerAdapter.addFragmentAndTitle(new MainHome(),"ការងារ");
        viewPagerAdapter.addFragmentAndTitle(new MainCV(), "បុគ្គលិក");
        viewPagerAdapter.addFragmentAndTitle(new MainSetting(), "គណនី");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);

    }


    @Override
    public void changeFragment(String fragmentName) {
    }
}
