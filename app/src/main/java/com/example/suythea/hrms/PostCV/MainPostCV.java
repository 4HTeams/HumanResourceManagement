package com.example.suythea.hrms.PostCV;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suythea.hrms.Interfaces.MySupporter_Interface;
import com.example.suythea.hrms.Interfaces.ViewOwn_Job_CV_Interface;
import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySqlite;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.example.suythea.hrms.ViewOwnCV.MainViewOwnCV;
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

public class MainPostCV extends AppCompatActivity implements MySupporter_Interface, DatePickerDialog.OnDateSetListener {

    Toolbar toolbar;
    String order;
    JSONArray pro, l_lvl, degree, contractType, job_cate;
    AlertDialog alert;
    DatePickerDialog datepickerdialog;

    EditText eTxtPubDate;
    EditText eTxtEndDate;

    Spinner spinGander, spinProvince;

    EditText eTxtTitle, eTxtFName, eTxtLName, eTxtPhone, eTxtAbout;

    ListView lisAccc, lisExp, lisLan, lisRef, lisSchool;

    Button btnAddACCC, btnAddExp, btnAddLan, btnAddRef, btnAddSchool;

    ScrollView scrollView;

    String oldCVID;

    ImageView img;

    ArrayList<ListPostCVModel> lisACCCModels;
    ArrayList<ListPostCVModel> lisExpModels;
    ArrayList<ListPostCVModel> lisLanModels;
    ArrayList<ListPostCVModel> lisRefModels;
    ArrayList<ListPostCVModel> lisSchoolModels;

    ListACCCAdp lisAcccAdp;
    ListExpAdp lisExpAdp;
    ListLanAdp lisLanAdp;
    ListRefAdp lisRefAdp;
    ListSchoolAdp lisSchoolAdp;

    ArrayList<HashMap> lisAcccData;
    ArrayList<HashMap> lisExpData;
    ArrayList<HashMap> lisLanData;
    ArrayList<HashMap> lisRefData;
    ArrayList<HashMap> lisSchoolData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_cv);

        MySupporter.runFirstDefault(this);

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
        lisAccc = (ListView)findViewById(R.id.lisACCCPostCV);
        lisExp = (ListView)findViewById(R.id.lisExpPostCV);
        lisLan = (ListView)findViewById(R.id.lisLanPostCV);
        lisRef = (ListView)findViewById(R.id.lisRefPostCV);
        lisSchool = (ListView)findViewById(R.id.lisSchoolPostCV);
        btnAddACCC = (Button)findViewById(R.id.btnAddACCCPostCV);
        btnAddExp = (Button)findViewById(R.id.btnAddExpPostCV);
        btnAddLan = (Button)findViewById(R.id.btnAddLanPostCV);
        btnAddRef = (Button)findViewById(R.id.btnAddRefPostCV);
        btnAddSchool = (Button)findViewById(R.id.btnAddSchoolPostCV);
        spinGander = (Spinner)findViewById(R.id.spinGanderPostCV);
        spinProvince = (Spinner)findViewById(R.id.spinProvincePostCV);
        eTxtTitle = (EditText)findViewById(R.id.eTxtTitlePostCV);
        eTxtFName = (EditText)findViewById(R.id.eTxtFNamePostCV);
        eTxtLName = (EditText)findViewById(R.id.eTxtLNamePostCV);
        eTxtPhone = (EditText)findViewById(R.id.eTxtPhonePostCV);
        eTxtAbout = (EditText)findViewById(R.id.eTxtAboutPostCV);
        scrollView = (ScrollView)findViewById(R.id.scrVPostCV);
        img = (ImageView)findViewById(R.id.imgProfileMainPostCV);
    }

    void setEvents(){
        btnAddACCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFormAccc("add", -1);
                hideKeyboard();
            }
        });

        btnAddExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFormExp("add", -1);
                hideKeyboard();
            }
        });

        btnAddLan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFormLan("add", -1);
                hideKeyboard();
            }
        });

        btnAddRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFormRef("add", -1);
                hideKeyboard();
            }
        });

        btnAddSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFormSchool("add", -1);
                hideKeyboard();
            }
        });

        lisAccc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menu_edit_post_cv:
                                loadFormAccc("edit", position);
                                break;

                            case R.id.menu_delete_post_cv:
                                lisACCCModels.remove(position);
                                lisAcccData.remove(position);
                                lisAcccAdp.notifyDataSetChanged();
                                setFullHeightListView(lisAccc);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });

        lisExp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menu_edit_post_cv:
                                loadFormExp("edit", position);
                                break;

                            case R.id.menu_delete_post_cv:
                                lisExpModels.remove(position);
                                lisExpData.remove(position);
                                lisExpAdp.notifyDataSetChanged();
                                setFullHeightListView(lisExp);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });

        lisLan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menu_edit_post_cv:
                                loadFormLan("edit", position);
                                break;

                            case R.id.menu_delete_post_cv:
                                lisLanModels.remove(position);
                                lisLanData.remove(position);
                                lisLanAdp.notifyDataSetChanged();
                                setFullHeightListView(lisLan);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });

        lisRef.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menu_edit_post_cv:
                                loadFormRef("edit", position);
                                break;

                            case R.id.menu_delete_post_cv:
                                lisRefModels.remove(position);
                                lisRefData.remove(position);
                                lisRefAdp.notifyDataSetChanged();
                                setFullHeightListView(lisRef);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });

        lisSchool.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Context wrapper = new ContextThemeWrapper(getBaseContext(), R.style.PopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_post_cv, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.menu_edit_post_cv:
                                loadFormSchool("edit", position);
                                break;

                            case R.id.menu_delete_post_cv:
                                lisSchoolModels.remove(position);
                                lisSchoolData.remove(position);
                                lisSchoolAdp.notifyDataSetChanged();
                                setFullHeightListView(lisSchool);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

                return false;
            }
        });
    }

    void startUp(){
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        this.pro = new JSONArray();
        this.l_lvl = new JSONArray();
        this.degree = new JSONArray();
        this.contractType = new JSONArray();
        this.job_cate = new JSONArray();

        lisACCCModels = new ArrayList<>();
        lisExpModels = new ArrayList<>();
        lisLanModels = new ArrayList<>();
        lisRefModels = new ArrayList<>();
        lisSchoolModels = new ArrayList<>();

        lisAcccData = new ArrayList<>();
        lisExpData = new ArrayList<>();
        lisLanData = new ArrayList<>();
        lisRefData = new ArrayList<>();
        lisSchoolData = new ArrayList<>();

        lisAcccAdp = new ListACCCAdp(this, R.layout.list_accc_postcv, lisACCCModels);
        lisAccc.setAdapter(lisAcccAdp);

        lisExpAdp = new ListExpAdp(this, R.layout.list_exp_postcv, lisExpModels);
        lisExp.setAdapter(lisExpAdp);

        lisLanAdp = new ListLanAdp(this, R.layout.list_language_postcv, lisLanModels);
        lisLan.setAdapter(lisLanAdp);

        lisRefAdp = new ListRefAdp(this, R.layout.list_ref_postcv, lisRefModels);
        lisRef.setAdapter(lisRefAdp);

        lisSchoolAdp = new ListSchoolAdp(this, R.layout.list_school_postcv, lisSchoolModels);
        lisSchool.setAdapter(lisSchoolAdp);

        order = getIntent().getStringExtra("order");
        this.setTitle(order);

        MySqlite sqlite = new MySqlite(this);

        String pro = sqlite.getDataFromjsonField(MySqlite.fields.get(3), "_all_db");
        String l_lvl = sqlite.getDataFromjsonField(MySqlite.fields.get(4), "_all_db");
        String degree = sqlite.getDataFromjsonField(MySqlite.fields.get(5), "_all_db");
        String contract_type = sqlite.getDataFromjsonField(MySqlite.fields.get(6), "_all_db");
        String job_cate = sqlite.getDataFromjsonField(MySqlite.fields.get(7), "_all_db");

        scrollView.setVisibility(View.GONE);

        if (pro.equals("") || l_lvl.equals("") || degree.equals("") || contract_type.equals("") || job_cate.equals("")){
            MySupporter.showLoading("Please Wait.....");
        }
        else {
            // Set data to array

            try {

                this.pro = new JSONArray(pro);
                this.l_lvl = new JSONArray(l_lvl);
                this.degree = new JSONArray(degree);
                this.contractType = new JSONArray(contract_type);
                this.job_cate = new JSONArray(job_cate);

            } catch (JSONException e) {e.printStackTrace();}

            loadSpinners();
        }

        getSpinnerDB();

        Picasso.with(this)
                .load("http://bongnu.myreading.xyz/profile_images/" + new MySqlite(this).getDataFromjsonField(MySqlite.fields.get(0),"id") + ".jpg")
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

    void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
    }

    void loadFormSchool(final String order, final int index){

        int i = 0;

        alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        final View view2 = inflater.inflate(R.layout.add_school,null);

        alert.setCancelable(false);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == 4){ // 4 is keyCode of BackButton
                    alert.dismiss();
                }
                return false;
            }
        });

        final EditText eTxtName = (EditText) view2.findViewById(R.id.eTxtNameAddSchool);
        final EditText eTxtStudy = (EditText) view2.findViewById(R.id.eTxtStudyAddSchool);
        final EditText eTxtGrade = (EditText) view2.findViewById(R.id.eTxtGradeAddSchool);
        final EditText eSTxtDate = (EditText) view2.findViewById(R.id.eTxtSDateAddSchool);

        eTxtPubDate = eSTxtDate;

        Button btnCancel = (Button)view2.findViewById(R.id.btnCancelSchool);
        Button btnAdd = (Button)view2.findViewById(R.id.btnAddSchool);
        Button btnSDate = (Button)view2.findViewById(R.id.btnAddSDateAddSchool);

        final Spinner spinDegree = (Spinner)view2.findViewById(R.id.spinDegreeAddSchool);

        final ArrayList<String> degrees = new ArrayList<>();

        while (i < degree.length()) {

            try {
                degrees.add(degree.getJSONObject(i).getString("dName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            i++;
        }

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, degrees);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDegree.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datepickerdialog = new DatePickerDialog(alert.getContext(),
                android.app.AlertDialog.THEME_HOLO_LIGHT, this,year,month,day);

        datepickerdialog.getDatePicker().setTag("1");

        if (order.equals("edit")){

            eTxtName.setText(String.valueOf(lisSchoolData.get(index).get("name")));
            eTxtStudy.setText(String.valueOf(lisSchoolData.get(index).get("study")));
            eTxtGrade.setText(String.valueOf(lisSchoolData.get(index).get("grade")));
            eSTxtDate.setText("Date : " + String.valueOf(lisSchoolData.get(index).get("sDate")));

            for (int j = 0; j < degree.length(); j++){
                try {
                    if (degree.getJSONObject(j).get("id").equals(lisSchoolData.get(index).get("degree"))){
                        spinDegree.setSelection(j);
                        break;
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }

        }

        btnSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eTxtName.getText().toString().equals("") || eTxtStudy.getText().toString().equals("") || eTxtGrade.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                ListPostCVModel model = new ListPostCVModel();
                model.setName(eTxtName.getText().toString());
                model.setDegree(spinDegree.getSelectedItem().toString());

                HashMap<String, String> map = new HashMap<>();
                map.put("name", eTxtName.getText().toString());
                map.put("study", eTxtStudy.getText().toString());
                map.put("grade", eTxtGrade.getText().toString());
                map.put("sDate", eSTxtDate.getText().toString().substring(eSTxtDate.getText().toString().indexOf(':') + 2, eSTxtDate.getText().toString().length()));
                try {
                    map.put("degree", String.valueOf(degree.getJSONObject((Integer) spinDegree.getSelectedItemPosition()).getString("id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (order.equals("edit")){
                    lisSchoolModels.set(index, model);
                    lisSchoolData.set(index, map);
                }
                else {
                    lisSchoolModels.add(model);
                    lisSchoolData.add(map);
                }

                lisSchoolAdp.notifyDataSetChanged();
                setFullHeightListView(lisSchool);

                Log.d("", String.valueOf(map));

                alert.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.setView(view2);
        alert.show();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideKeyboard();
            }
        });

    }

    void loadFormRef(final String order, final int index){

        int i = 0;

        alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        final View view2 = inflater.inflate(R.layout.add_ref,null);

        alert.setCancelable(false);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == 4){ // 4 is keyCode of BackButton
                    alert.dismiss();
                }
                return false;
            }
        });

        final EditText eTxtTitle = (EditText) view2.findViewById(R.id.eTxtJTitleAddRef);
        final EditText eTxtName = (EditText) view2.findViewById(R.id.eTxtNameAddRef);
        final EditText eTxtCom = (EditText) view2.findViewById(R.id.eTxtComAddRef);
        final EditText eTxtPhone = (EditText) view2.findViewById(R.id.eTxtPhoneAddRef);
        final EditText eTxtEmail = (EditText) view2.findViewById(R.id.eTxtEmailAddRef);

        Button btnCancel = (Button)view2.findViewById(R.id.btnCancelRef);
        Button btnAdd = (Button)view2.findViewById(R.id.btnAddRef);

        if (order.equals("edit")){
            eTxtTitle.setText(String.valueOf(lisRefData.get(index).get("title")));
            eTxtName.setText(String.valueOf(lisRefData.get(index).get("name")));
            eTxtCom.setText(String.valueOf(lisRefData.get(index).get("com")));
            eTxtPhone.setText(String.valueOf(lisRefData.get(index).get("phone")));
            eTxtEmail.setText(String.valueOf(lisRefData.get(index).get("email")));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eTxtTitle.getText().toString().equals("") || eTxtName.getText().toString().equals("") || eTxtCom.getText().toString().equals("") || eTxtPhone.getText().toString().equals("") || eTxtEmail.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                ListPostCVModel model = new ListPostCVModel();
                model.setTitle(eTxtTitle.getText().toString());
                model.setName(eTxtName.getText().toString());

                HashMap<String, String> map = new HashMap<>();
                map.put("title", eTxtTitle.getText().toString());
                map.put("name", eTxtName.getText().toString());
                map.put("com", eTxtCom.getText().toString());
                map.put("phone", eTxtPhone.getText().toString());
                map.put("email", eTxtEmail.getText().toString());

                if (order.equals("edit")){
                    lisRefModels.set(index, model);
                    lisRefData.set(index, map);
                }
                else {
                    lisRefModels.add(model);
                    lisRefData.add(map);
                }

                lisRefAdp.notifyDataSetChanged();
                setFullHeightListView(lisRef);

                Log.d("", String.valueOf(map));

                alert.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.setView(view2);
        alert.show();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideKeyboard();
            }
        });

    }

    void loadFormLan(final String order, final int index){

        int i = 0;

        alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        final View view2 = inflater.inflate(R.layout.add_lan,null);

        alert.setCancelable(false);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == 4){ // 4 is keyCode of BackButton
                    alert.dismiss();
                }
                return false;
            }
        });

        final EditText eTxtLan = (EditText) view2.findViewById(R.id.eTxtLanAddLan);

        Button btnCancel = (Button)view2.findViewById(R.id.btnCancelLan);
        Button btnAdd = (Button)view2.findViewById(R.id.btnAddLan);

        final Spinner spinLan = (Spinner)view2.findViewById(R.id.spinLanAddLan);

        final ArrayList<String> llevel = new ArrayList<>();

        while (i < l_lvl.length()) {

            try {
                llevel.add(l_lvl.getJSONObject(i).getString("llName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            i++;
        }

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, llevel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLan.setAdapter(adapter);

        if (order.equals("edit")){

            eTxtLan.setText(String.valueOf(lisLanData.get(index).get("lName")));

            for (int j = 0; j < l_lvl.length(); j++){
                try {
                    if (l_lvl.getJSONObject(j).get("id").equals(lisLanData.get(index).get("l_lvl"))){
                        spinLan.setSelection(j);
                        break;
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eTxtLan.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                ListPostCVModel model = new ListPostCVModel();
                model.setName(eTxtLan.getText().toString());
                model.setLevel(spinLan.getSelectedItem().toString());

                HashMap<String, String> map = new HashMap<>();
                map.put("lName", eTxtLan.getText().toString());
                try {
                    map.put("l_lvl", String.valueOf(l_lvl.getJSONObject((Integer) spinLan.getSelectedItemPosition()).getString("id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (order.equals("edit")) {
                    lisLanData.set(index, map);
                    lisLanModels.set(index, model);
                }
                else{
                    lisLanData.add(map);
                    lisLanModels.add(model);
                }

                lisLanAdp.notifyDataSetChanged();
                setFullHeightListView(lisLan);

                Log.d("", String.valueOf(map));

                alert.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.setView(view2);
        alert.show();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideKeyboard();
            }
        });

    }

    void loadFormExp(final String order, final int index){

        int i = 0;

        alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        final View view2 = inflater.inflate(R.layout.add_exp,null);

        alert.setCancelable(false);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == 4){ // 4 is keyCode of BackButton
                    alert.dismiss();
                }
                return false;
            }
        });

        final EditText eTxtTitle = (EditText) view2.findViewById(R.id.eTxtTitleAddExp);
        final EditText eTxtName = (EditText) view2.findViewById(R.id.eTxtNameAddExp);
        final EditText eTxtActivity = (EditText) view2.findViewById(R.id.eTxtActivityAddExp);
        final EditText eTxtSDate = (EditText) view2.findViewById(R.id.eTxtSDateAddExp);
        final EditText eTxtEDate = (EditText) view2.findViewById(R.id.eTxtEDateAddExp);

        eTxtPubDate = eTxtSDate;
        eTxtEndDate = eTxtEDate;

        Button btnCancel = (Button)view2.findViewById(R.id.btnCancelExp);
        Button btnAdd = (Button)view2.findViewById(R.id.btnAddExp);

        Button btnAddSDate = (Button)view2.findViewById(R.id.btnAddSDateAddExp);
        Button btnAddEDate = (Button)view2.findViewById(R.id.btnAddEDateAddExp);

        final Spinner spinJR = (Spinner)view2.findViewById(R.id.spinJobRoleExp);
        final Spinner spinCType = (Spinner)view2.findViewById(R.id.spinCTypeExp);

        ArrayList<String> jobRoles = new ArrayList<>();
        ArrayList<String> cType = new ArrayList<>();

        while (i < job_cate.length()) {

            try {
                jobRoles.add(job_cate.getJSONObject(i).getString("jcName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            i++;
        }
        i = 0;

        while (i < contractType.length()) {

            try {
                cType.add(contractType.getJSONObject(i).getString("ctName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            i++;
        }
        i = 0;

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jobRoles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJR.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCType.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datepickerdialog = new DatePickerDialog(alert.getContext(),
                android.app.AlertDialog.THEME_HOLO_LIGHT, this,year,month,day);

        if (order.equals("edit")){

            eTxtTitle.setText(String.valueOf(lisExpData.get(index).get("title")));
            eTxtName.setText(String.valueOf(lisExpData.get(index).get("name")));
            eTxtActivity.setText(String.valueOf(lisExpData.get(index).get("activity")));
            eTxtSDate.setText("Date : " + String.valueOf(lisExpData.get(index).get("sDate")));
            eTxtEDate.setText("Date : " + String.valueOf(lisExpData.get(index).get("eDate")));

            for (int j = 0; j < job_cate.length(); j++){
                try {
                    if (job_cate.getJSONObject(j).get("id").equals(lisExpData.get(index).get("jr"))){
                        spinJR.setSelection(j);
                        break;
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }

            for (int j1 = 0; j1 < contractType.length(); j1++){
                try {
                    if (contractType.getJSONObject(j1).get("id").equals(lisExpData.get(index).get("ct"))){
                        spinCType.setSelection(j1);
                        break;
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eTxtTitle.getText().toString().equals("") || eTxtName.getText().toString().equals("") || eTxtActivity.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                ListPostCVModel model = new ListPostCVModel();
                model.setTitle(eTxtTitle.getText().toString());
                model.setName(eTxtName.getText().toString());

                HashMap<String, String> map = new HashMap<>();
                map.put("title", eTxtTitle.getText().toString());
                map.put("name", eTxtName.getText().toString());
                map.put("activity", eTxtActivity.getText().toString());
                map.put("sDate", eTxtSDate.getText().toString().substring(eTxtSDate.getText().toString().indexOf(':') + 2, eTxtSDate.getText().toString().length()));
                map.put("eDate", eTxtEDate.getText().toString().substring(eTxtEDate.getText().toString().indexOf(':') + 2, eTxtEDate.getText().toString().length()));
                try {
                    map.put("jr", String.valueOf(job_cate.getJSONObject((Integer) spinJR.getSelectedItemPosition()).getString("id")));
                    map.put("ct", String.valueOf(contractType.getJSONObject((Integer) spinCType.getSelectedItemPosition()).getString("id")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (order.equals("edit")){
                    lisExpModels.set(index, model);
                    lisExpData.set(index, map);
                }
                else {
                    lisExpModels.add(model);
                    lisExpData.add(map);
                }

                lisExpAdp.notifyDataSetChanged();
                setFullHeightListView(lisExp);

                Log.d("result", String.valueOf(map));

                alert.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        btnAddSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog.getDatePicker().setTag("1");
                datepickerdialog.show();
            }
        });

        btnAddEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog.getDatePicker().setTag("2");
                datepickerdialog.show();
            }
        });

        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.setView(view2);
        alert.show();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideKeyboard();
            }
        });

    }

    void loadFormAccc(final String order, final int index){

        alert = new AlertDialog.Builder(this).create();

        LayoutInflater inflater = getLayoutInflater();
        final View view2 = inflater.inflate(R.layout.add_accc,null);

        alert.setCancelable(false);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == 4){ // 4 is keyCode of BackButton
                    alert.dismiss();
                }
                return false;
            }
        });

        final EditText eTxtTitle = (EditText) view2.findViewById(R.id.eTxtTitleAddAccc);
        final EditText eTxtDate = (EditText) view2.findViewById(R.id.eTxtDateAddAccc);
        final EditText eTxtAbout = (EditText) view2.findViewById(R.id.eTxtAboutAddAccc);

        eTxtPubDate = eTxtDate;

        if (order.equals("edit")){

            eTxtTitle.setText(String.valueOf(lisAcccData.get(index).get("title")));
            eTxtAbout.setText(String.valueOf(lisAcccData.get(index).get("about")));
            eTxtDate.setText("Date : " + String.valueOf(lisAcccData.get(index).get("date")));

        }

        Button btnCancel = (Button)view2.findViewById(R.id.btnCancelAccc);
        Button btnAdd = (Button)view2.findViewById(R.id.btnAddAccc);
        Button btnAddDate = (Button)view2.findViewById(R.id.btnAddDateAddAccc);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datepickerdialog = new DatePickerDialog(alert.getContext(),
                android.app.AlertDialog.THEME_HOLO_LIGHT, this,year,month,day);

        datepickerdialog.getDatePicker().setTag("1");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eTxtTitle.getText().toString().equals("") || eTxtAbout.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes", Toast.LENGTH_LONG).show();
                    return;
                }

                ListPostCVModel model = new ListPostCVModel();
                model.setTitle(eTxtTitle.getText().toString());
                model.setDate(eTxtDate.getText().toString().substring(eTxtDate.getText().toString().indexOf(':') + 2, eTxtDate.getText().toString().length()));

                HashMap<String, String> map = new HashMap<>();
                map.put("title", eTxtTitle.getText().toString());
                map.put("about", eTxtAbout.getText().toString());
                map.put("date", eTxtDate.getText().toString().substring(eTxtDate.getText().toString().indexOf(':') + 2, eTxtDate.getText().toString().length()));

                if (order.equals("edit")){
                    lisACCCModels.set(index, model);
                    lisAcccData.set(index, map);
                }
                else{
                    lisACCCModels.add(model);
                    lisAcccData.add(map);
                }

                lisAcccAdp.notifyDataSetChanged();
                setFullHeightListView(lisAccc);

                alert.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog.show();
            }
        });

        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.setView(view2);
        alert.show();

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideKeyboard();
            }
        });

    }

    void loadSpinners () {

        scrollView.setVisibility(View.VISIBLE);

        try{

            int i = 0;
            ArrayList<String> gander, provinces;

            gander = new ArrayList<>();
            gander.add("Male");
            gander.add("Female");

            provinces = new ArrayList<>();

            while (i < pro.length()) {

                provinces.add(pro.getJSONObject(i).getString("proName"));
                i++;
            }

            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gander);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinGander.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinces);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinProvince.setAdapter(adapter);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        loadOldCV ();
    }

    void loadOldCV(){

        try {
            if (!order.equals("EDIT")){
                return;
            }

            HashMap<String, String> map = new HashMap<>();
            ListPostCVModel model = new ListPostCVModel();

            JSONArray data = new JSONArray(URLDecoder.decode(URLEncoder.encode(MainViewOwnCV.oldCVData, "iso8859-1"),"UTF-8"));
            JSONObject CV = new JSONArray(data.getJSONObject(0).getString("CV")).getJSONObject(0);
            JSONArray Accc = new JSONArray();
            JSONArray Exp = new JSONArray();
            JSONArray Ref = new JSONArray();
            JSONArray Lan = new JSONArray();
            JSONArray School = new JSONArray();

            try{ Accc = new JSONArray(data.getJSONObject(0).getString("Accc"));} catch (JSONException e) {e.printStackTrace();}
            try{ Exp = new JSONArray(data.getJSONObject(0).getString("Exp")); } catch (JSONException e) {e.printStackTrace();}
            try{ Ref = new JSONArray(data.getJSONObject(0).getString("Ref")); } catch (JSONException e) {e.printStackTrace();}
            try{ Lan = new JSONArray(data.getJSONObject(0).getString("lan")); } catch (JSONException e) {e.printStackTrace();}
            try{ School = new JSONArray(data.getJSONObject(0).getString("School")); } catch (JSONException e) {e.printStackTrace();}

            oldCVID = CV.getString("id");

            eTxtTitle.setText(CV.getString("title"));
            eTxtFName.setText(CV.getString("fName"));
            eTxtLName.setText(CV.getString("lName"));
            eTxtPhone.setText(CV.getString("phone"));
            eTxtAbout.setText(CV.getString("about"));

            spinGander.setSelection(CV.getString("gender").equals("Male") ? 0 : 1);

            for (int i=0; i < pro.length(); i++){
                if (pro.getJSONObject(i).getString("id").equals(CV.getString("pID"))){
                    spinProvince.setSelection(i);
                    break;
                }
            }

            for (int i=0; i<Accc.length(); i++){

                model = new ListPostCVModel();
                map = new HashMap<>();

                model.setTitle(Accc.getJSONObject(i).getString("title"));
                model.setDate(Accc.getJSONObject(i).getString("date"));

                map.put("title", Accc.getJSONObject(i).getString("title"));
                map.put("about", Accc.getJSONObject(i).getString("des"));
                map.put("date", Accc.getJSONObject(i).getString("date"));


                lisACCCModels.add(model);
                lisAcccData.add(map);
            }



            for (int i=0; i<Exp.length(); i++){

                model = new ListPostCVModel();
                map = new HashMap<>();

                model.setTitle(Exp.getJSONObject(i).getString("jTitle"));
                model.setName(Exp.getJSONObject(i).getString("comName"));

                map.put("title", Exp.getJSONObject(i).getString("jTitle"));
                map.put("name", Exp.getJSONObject(i).getString("comName"));
                map.put("activity", Exp.getJSONObject(i).getString("activity"));
                map.put("sDate", Exp.getJSONObject(i).getString("sDate"));
                map.put("eDate", Exp.getJSONObject(i).getString("eDate"));
                map.put("jr", Exp.getJSONObject(i).getString("jtID"));
                map.put("ct", Exp.getJSONObject(i).getString("ctID"));

                lisExpModels.add(model);
                lisExpData.add(map);
            }

            for (int i=0; i<Ref.length(); i++){

                model = new ListPostCVModel();
                map = new HashMap<>();

                model.setTitle(Ref.getJSONObject(i).getString("title"));
                model.setName(Ref.getJSONObject(i).getString("name"));

                map.put("title", Ref.getJSONObject(i).getString("title"));
                map.put("name", Ref.getJSONObject(i).getString("name"));
                map.put("com", Ref.getJSONObject(i).getString("com"));
                map.put("phone", Ref.getJSONObject(i).getString("tel"));
                map.put("email", Ref.getJSONObject(i).getString("email"));

                lisRefModels.add(model);
                lisRefData.add(map);
            }

            for (int i=0; i<Lan.length(); i++){

                model = new ListPostCVModel();
                map = new HashMap<>();

                model.setName(Lan.getJSONObject(i).getString("lName"));
                model.setLevel(Lan.getJSONObject(i).getString("l_level"));

                map.put("lName", Lan.getJSONObject(i).getString("lName"));
                map.put("l_lvl", Lan.getJSONObject(i).getString("lID"));

                lisLanModels.add(model);
                lisLanData.add(map);
            }

            for (int i=0; i<School.length(); i++){

                model = new ListPostCVModel();
                map = new HashMap<>();

                model.setName(School.getJSONObject(i).getString("sName"));
                model.setDegree(School.getJSONObject(i).getString("degree"));

                map.put("name", School.getJSONObject(i).getString("sName"));
                map.put("study", School.getJSONObject(i).getString("study"));
                map.put("grade", School.getJSONObject(i).getString("grade"));
                map.put("sDate", School.getJSONObject(i).getString("sDate"));
                map.put("degree", School.getJSONObject(i).getString("dID"));

                lisSchoolModels.add(model);
                lisSchoolData.add(map);
            }

        } catch (JSONException e) {e.printStackTrace();} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        lisAcccAdp.notifyDataSetChanged();
        lisRefAdp.notifyDataSetChanged();
        lisLanAdp.notifyDataSetChanged();
        lisSchoolAdp.notifyDataSetChanged();
        lisExpAdp.notifyDataSetChanged();

        setFullHeightListView(lisAccc);
        setFullHeightListView(lisRef);
        setFullHeightListView(lisLan);
        setFullHeightListView(lisSchool);
        setFullHeightListView(lisExp);

    }

    void getSpinnerDB(){
        MySupporter.Volley("http://bongnu.myreading.xyz/bongnu/get_data_tbl.php?appToken=ThEa331RA369RiTH383thY925&province=1&lan_lvl=1&degree=1&contractType=1&job_cate=1",new HashMap<String, String>(),this);
    }

    void setFullHeightListView (ListView listView){
        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    @Override
    public void onHttpFinished(String response) {

        MySupporter.hideLoading();

        try {

            JSONArray arrayDB = new JSONArray(URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8"));
            JSONObject jsonObj = arrayDB.getJSONObject(0);

            if (jsonObj.getString("status").equals("Success")){
                if (order.equals("EDIT")){
                    ViewOwn_Job_CV_Interface viewCVInterface = (ViewOwn_Job_CV_Interface) MainViewOwnCV.context;
                    JSONArray data = new JSONArray(URLDecoder.decode(URLEncoder.encode(MainViewOwnCV.oldCVData, "iso8859-1"),"UTF-8"));
                    JSONObject CV = new JSONArray(data.getJSONObject(0).getString("CV")).getJSONObject(0);
                    JSONObject object = new JSONObject();

                    object.put("id", CV.getString("id"));
                    object.put("fName", eTxtFName.getText().toString());
                    object.put("lName", eTxtLName.getText().toString());
                    object.put("posted_date", CV.getString("posted_date"));
                    object.put("title", eTxtTitle.getText().toString());

                    viewCVInterface.reloadChangedData(object);
                }

                finish();
            }
            else if (jsonObj.getString("status").equals("ErrorPassword")){
                Toast.makeText(this, jsonObj.getString("Message"), Toast.LENGTH_LONG).show();
            }

            Log.d("result", response);
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onHttpError(String message) {
        MySupporter.hideLoading();
        Toast.makeText(this, MySupporter.checkError(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVolleyFinished(String response) {

        try {
            String data = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"),"UTF-8");

            String pro = new JSONArray(data).getJSONObject(0).getString("provinces");
            String l_lvl = new JSONArray(data).getJSONObject(0).getString("lan_lvl");
            String degree = new JSONArray(data).getJSONObject(0).getString("degree");
            String contractType = new JSONArray(data).getJSONObject(0).getString("contractType");
            String job_cate = new JSONArray(data).getJSONObject(0).getString("job_cate");

            if (this.pro.length() < 1 || this.l_lvl.length() < 1 || this.degree.length() < 1 || this.contractType.length() < 1 || this.job_cate.length() < 1){

                this.pro = new JSONArray(pro);
                this.l_lvl = new JSONArray(l_lvl);
                this.degree = new JSONArray(degree);
                this.contractType = new JSONArray(contractType);
                this.job_cate = new JSONArray(job_cate);

                loadSpinners();
            }

            MySqlite sqlite = new MySqlite(this);
            sqlite.insertJsonDB(MySqlite.fields.get(3), pro);
            sqlite.insertJsonDB(MySqlite.fields.get(4), l_lvl);
            sqlite.insertJsonDB(MySqlite.fields.get(5), degree);
            sqlite.insertJsonDB(MySqlite.fields.get(6), contractType);
            sqlite.insertJsonDB(MySqlite.fields.get(7), job_cate);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MySupporter.hideLoading();
    }

    @Override
    public void onVolleyError(String message) {
        MySupporter.hideLoading();

        if (this.pro.length() < 1 || this.l_lvl.length() < 1 || this.degree.length() < 1 || this.contractType.length() < 1 || this.job_cate.length() < 1) {
            // No data in database and no error getting data from web ---> finish this activity
            Toast.makeText(this,MySupporter.checkError(),Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){

            case android.R.id.home :
                finish();
                break;

            case R.id.icSend :
                hideKeyboard();

                if (eTxtTitle.getText().toString().equals("") || eTxtFName.getText().toString().equals("") || eTxtLName.getText().toString().equals("") || eTxtPhone.getText().toString().equals("") || eTxtAbout.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Fill all textboxes !", Toast.LENGTH_LONG).show();
                    break;
                }

                prepareForHttp();
                break;
        }
        return true;
    }

    private void prepareForHttp (){

        try {

            JSONObject mainArray = new JSONObject();
            JSONObject object = new JSONObject();

            JSONArray accc = new JSONArray();
            JSONArray exp = new JSONArray();
            JSONArray lan = new JSONArray();
            JSONArray ref = new JSONArray();
            JSONArray school = new JSONArray();

            for (int i = 0; i < lisAcccData.size(); i++){
                object = new JSONObject();
                object.put("title", lisAcccData.get(i).get("title"));
                object.put("about", lisAcccData.get(i).get("about"));
                object.put("date", lisAcccData.get(i).get("date"));
                accc.put(object);
            }


            for (int i = 0; i < lisExpData.size(); i++){
                object = new JSONObject();
                object.put("title", lisExpData.get(i).get("title"));
                object.put("name", lisExpData.get(i).get("name"));
                object.put("activity", lisExpData.get(i).get("activity"));
                object.put("sDate", lisExpData.get(i).get("sDate"));
                object.put("eDate", lisExpData.get(i).get("eDate"));
                object.put("jr", lisExpData.get(i).get("jr"));
                object.put("ct", lisExpData.get(i).get("ct"));
                exp.put(object);
            }


            for (int i = 0; i < lisLanData.size(); i++){
                object = new JSONObject();
                object.put("lName", lisLanData.get(i).get("lName"));
                object.put("l_lvl", lisLanData.get(i).get("l_lvl"));
                lan.put(object);
            }


            for (int i = 0; i < lisRefData.size(); i++){
                object = new JSONObject();
                object.put("title", lisRefData.get(i).get("title"));
                object.put("name", lisRefData.get(i).get("name"));
                object.put("com", lisRefData.get(i).get("com"));
                object.put("phone", lisRefData.get(i).get("phone"));
                object.put("email", lisRefData.get(i).get("email"));
                ref.put(object);
            }


            for (int i = 0; i < lisSchoolData.size(); i++){
                object = new JSONObject();
                object.put("name", lisSchoolData.get(i).get("name"));
                object.put("study", lisSchoolData.get(i).get("study"));
                object.put("grade", lisSchoolData.get(i).get("grade"));
                object.put("sDate", lisSchoolData.get(i).get("sDate"));
                object.put("degree", lisSchoolData.get(i).get("degree"));
                school.put(object);
            }

            mainArray.put("accc", accc);
            mainArray.put("exp", exp);
            mainArray.put("lan", lan);
            mainArray.put("ref", ref);
            mainArray.put("school", school);

            MySqlite sqlite = new MySqlite(this);

            HashMap<String, String> params = new HashMap<>();

            params.put("appToken","ThEa331RA369RiTH383thY925");
            params.put("id",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"id"));
            params.put("conPassword",sqlite.getDataFromjsonField(MySqlite.fields.get(0),"password"));
            params.put("listData", String.valueOf(mainArray));
            params.put("title", eTxtTitle.getText().toString());
            params.put("fName", eTxtFName.getText().toString());
            params.put("lName", eTxtLName.getText().toString());
            params.put("gander", String.valueOf(spinGander.getSelectedItem()));
            params.put("phone", eTxtPhone.getText().toString());
            params.put("pro", String.valueOf(pro.getJSONObject((Integer) spinProvince.getSelectedItemPosition()).getString("id")));
            params.put("about", eTxtAbout.getText().toString());

            beforeHttp(params);

            Log.d("result", String.valueOf(params));
        }
        catch (JSONException e) {
            Log.d("", "Error prepareForHttp");
            e.printStackTrace();
        }
    }

    private void beforeHttp(HashMap<String, String> map){

        MySupporter.showLoading("Please Wait.....");

        if (order.equals("POST")){
            map.put("order", "POST");
            MySupporter.Http("http://bongnu.myreading.xyz/bongnu/cv/post_cv.php", map, this);
        }
        else {
            map.put("order", "EDIT");
            map.put("emp_id", oldCVID);
            MySupporter.Http("http://bongnu.myreading.xyz/bongnu/cv/post_cv.php", map, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = format.format(calendar.getTime());

        if (view.getTag().equals("1")){
            eTxtPubDate.setText("Date : " + strDate);
        }
        else if (view.getTag().equals("2")){
            eTxtEndDate.setText("Date : " + strDate);
        }
    }
}