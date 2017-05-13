package com.example.suythea.hrms;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.example.suythea.hrms.CV.MainCV;
import com.example.suythea.hrms.Home.MainHome;
import com.example.suythea.hrms.SearchJob.MainSearchJob;
import com.example.suythea.hrms.Setting.MainSetting;
import com.example.suythea.hrms.Interfaces.Main_Interface;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewCV.MainViewCV;

public class MainActivity extends AppCompatActivity implements Main_Interface{

    public static Toolbar toolbar;
    public static Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControls();
        setEvents();
        startUp();
    }

    void setControls() {
        searchView = (SearchView) findViewById(R.id.mySearch);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    void setEvents(){

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    searchView.setQueryHint("ស្វែងរក ការងារ.....");
                    searchView.setVisibility(View.VISIBLE);
                }
                else if (tab.getPosition() == 1){
                    searchView.setVisibility(View.GONE);
                    MainCV.startGettingData();
                }
                else if (tab.getPosition() == 2){

                    searchView.setVisibility(View.GONE);

                    if (MainSetting.showingLoading && !MainSetting.finishedAllData){

                        if (!MainSetting.type.equals("")){

                            // Don't show progress bar when no account logged in
                            MySupporter.showLoading("Checking Account.....");
                        }

                        // Check account logged in
                        MainSetting.checkReloginFromInterface();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        context = this;

        // MySupporter.runFirstDefault(this) is used to pass context to MySupporter class to define controls
        MySupporter.runFirstDefault(this);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    if (hasFocus){
                        v.clearFocus();

                        Intent i = new Intent(getBaseContext(), MainSearchJob.class);
                        startActivity(i);
                    }

                }
            }
        });
    }

    @Override
    public void changeTapIndex(int index) {
        try {
            tabLayout.getTabAt(index).select();
        }catch (Exception e){}
    }

}
