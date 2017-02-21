package com.example.suythea.hrms;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.suythea.hrms.CV.MainCV;
import com.example.suythea.hrms.Home.MainHome;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Interfaces.Main_Interface;

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
        setTitle("ស្វាគមន៍មកកាន់ បងន័រតុន");
        setSupportActionBar(toolbar);

        viewPagerAdapter.addFragmentAndTitle(new MainHome(this),"ការងារ");
        viewPagerAdapter.addFragmentAndTitle(new MainCV(this), "បុគ្គលិក");
        viewPagerAdapter.addFragmentAndTitle(new MainSetting(this), "គណនី");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);

    }


    @Override
    public void changeFragment(String fragmentName) {
    }
}
