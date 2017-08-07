package com.example.suythea.hrms.ViewCV;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.R;
import com.example.suythea.hrms.Supporting_Files.MySupporter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainViewCV extends AppCompatActivity {

    Toolbar toolbar;
    String mainData,dataCV,dataAcc,dataExp,dataRef,dataLang,dataSchool;
    TextView txtFirstName,txtLastName,txtGender,txtPhone,txtProvince,txtAbout,txtTitle,txtPostDate;
    TextView txtDesc,txtJobTitle,txtCompanyName,txtStartDate,txtEndDate,txtJcName,txtCtName,txtActivity;
    ImageView profileImage;

    RecyclerView lsAccomplishment,lsReference,lsLanguage,lsSchool;


    ArrayList<AccomplishModel> arrayAccomplist = new ArrayList<>();
    ArrayList<ReferenceModel> arrayRef = new ArrayList<>();
    ArrayList<LanguageModel> arrayLang= new ArrayList<>();
    ArrayList<SchoolModel> arraySchool = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_cv);

        mainData = getIntent().getStringExtra("response");

        Log.d("result",mainData);

        setupView();

        setControls();
        setEvents();
        startUp();
        setupData();
    }

    void setupData(){

        try {
            JSONArray jsonArray = new JSONArray(mainData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            dataCV = jsonObject.getString("CV");
            JSONArray jCVArray = new JSONArray(dataCV);
            JSONObject jCVObject = jCVArray.getJSONObject(0);

            // CV
            this.setProfileImage(jCVObject.getString("uid"));
            txtFirstName.setText(jCVObject.getString("fName"));
            txtLastName.setText(jCVObject.getString("lName"));
            txtGender.setText(jCVObject.getString("gender"));
            txtPhone.setText(jCVObject.getString("phone"));
            txtProvince.setText(jCVObject.getString("pName"));
            txtAbout.setText(jCVObject.getString("about"));

            // ACC
            dataAcc = jsonObject.getString("Accc");
            JSONArray jACCArray = new JSONArray(dataAcc);

            for (int i = 0; i < jACCArray.length(); i++) {
                AccomplishModel modelAccomplist = new AccomplishModel();
                modelAccomplist.title = jACCArray.getJSONObject(i).getString("title");
                modelAccomplist.date = jACCArray.getJSONObject(i).getString("date");
                modelAccomplist.des = jACCArray.getJSONObject(i).getString("des");

                arrayAccomplist.add(modelAccomplist);
            }
            lsAccomplishment.setLayoutManager(new LinearLayoutManager(this));
            lsAccomplishment.setAdapter(new ListJobAdapter(0));


            // EXP
            dataExp = jsonObject.getString("Exp");
            JSONArray jExpArray = new JSONArray(dataExp);
            JSONObject jExpObject = jExpArray.getJSONObject(0);
            txtJobTitle.setText(jExpObject.getString("jTitle"));
            txtCompanyName.setText(jExpObject.getString("comName"));
            txtStartDate.setText(jExpObject.getString("sDate"));
            txtEndDate.setText(jExpObject.getString("eDate"));
            txtJcName.setText(jExpObject.getString("jcName"));
            txtCtName.setText(jExpObject.getString("ctName"));
            txtActivity.setText(jExpObject.getString("activity"));

            //Reference
            dataRef = jsonObject.getString("Ref");
            JSONArray jRefArray = new JSONArray(dataRef);

            for (int i = 0; i < jRefArray.length(); i++) {
                ReferenceModel referenceModel = new ReferenceModel();
                referenceModel.name = jRefArray.getJSONObject(i).getString("name");
                referenceModel.title = jRefArray.getJSONObject(i).getString("title");
                referenceModel.tel = jRefArray.getJSONObject(i).getString("tel");
                referenceModel.email = jRefArray.getJSONObject(i).getString("email");
                referenceModel.com = jRefArray.getJSONObject(i).getString("com");


                arrayRef.add(referenceModel);
            }
            lsReference.setLayoutManager(new LinearLayoutManager(this));
            lsReference.setAdapter(new ListJobAdapter(1));

            //Language

            dataLang = jsonObject.getString("lan");
            JSONArray jLangArray = new JSONArray(dataLang);

            for (int i = 0; i < jLangArray.length(); i++) {
                LanguageModel languageModel = new LanguageModel();
                languageModel.name = jLangArray.getJSONObject(i).getString("lName");
                languageModel.level = jLangArray.getJSONObject(i).getString("l_level");

                arrayLang.add(languageModel);
            }
            lsLanguage.setLayoutManager(new LinearLayoutManager(this));
            lsLanguage.setAdapter(new ListJobAdapter(2));


            //School

            dataSchool = jsonObject.getString("School");
            JSONArray jSchoolArray = new JSONArray(dataSchool);

            for (int i = 0; i < jSchoolArray.length(); i++) {
                SchoolModel schoolModel = new SchoolModel();
                schoolModel.sName = jSchoolArray.getJSONObject(i).getString("sName");
                schoolModel.sDate = jSchoolArray.getJSONObject(i).getString("sDate");
                schoolModel.study = jSchoolArray.getJSONObject(i).getString("study");
                schoolModel.grade = jSchoolArray.getJSONObject(i).getString("grade");
                schoolModel.degree = jSchoolArray.getJSONObject(i).getString("degree");
                arraySchool.add(schoolModel);
            }
            lsSchool.setLayoutManager(new LinearLayoutManager(this));
            lsSchool.setAdapter(new ListJobAdapter(3));

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

        setTitle("View CV");
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


    void setupView() {
        txtFirstName = (TextView) findViewById(R.id.txtFistName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtGender= (TextView) findViewById(R.id.txtGender);
        txtPhone= (TextView) findViewById(R.id.txtPhone);
        txtProvince= (TextView) findViewById(R.id.txtProvince);
        txtAbout= (TextView) findViewById(R.id.txtAbout);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtPostDate= (TextView) findViewById(R.id.txtPostDate);
        txtDesc= (TextView) findViewById(R.id.txtDescription);
        txtJobTitle= (TextView) findViewById(R.id.txtJobTitle);
        txtCompanyName= (TextView) findViewById(R.id.txtCompanyName);
        txtStartDate= (TextView) findViewById(R.id.txtStartDate);
        txtEndDate= (TextView) findViewById(R.id.txtEndDate);
        txtJcName= (TextView) findViewById(R.id.txtJobCategory);
        txtCtName= (TextView) findViewById(R.id.txtContractType);
        txtActivity= (TextView) findViewById(R.id.txtActivity);

        profileImage = (ImageView) findViewById(R.id.profileImage);

        lsAccomplishment = (RecyclerView) findViewById(R.id.accomplishmentList);
        lsReference = (RecyclerView) findViewById(R.id.referenceList);
        lsLanguage= (RecyclerView) findViewById(R.id.languageList);
        lsSchool = (RecyclerView) findViewById(R.id.schoolList);

    }


    public class ListJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int type;
        public ListJobAdapter(int type){
            this.type = type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(type == 0){
                return new accomplishHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_cv_list_accomplish,parent,false));
            }else if (type == 1) {
                return new referenceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_cv_list_reference,parent,false));
            }else if (type == 2){
                return new languageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_cv_list_language,parent,false));
            }else if (type == 3){
                return new schoolHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_cv_list_school,parent,false));
            }
            else return new accomplishHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_cv_list_accomplish,parent,false));

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(type == 0) {
                ((accomplishHolder) holder).tvTitle.setText(arrayAccomplist.get(position).getTitle());
                ((accomplishHolder) holder).tvDate.setText(arrayAccomplist.get(position).getDate());
                ((accomplishHolder) holder).tvDes.setText(arrayAccomplist.get(position).getDes());
            }else if (type == 1) {
                ((referenceHolder) holder).tvName.setText(arrayRef.get(position).getName());
                ((referenceHolder) holder).tvTitle.setText(arrayRef.get(position).getTitle());
                ((referenceHolder) holder).tvTel.setText(arrayRef.get(position).getTel());
                ((referenceHolder) holder).tvEmail.setText(arrayRef.get(position).getEmail());
                ((referenceHolder) holder).tvCompany.setText(arrayRef.get(position).getCom());
            }else if (type == 2){
                ((languageHolder) holder).tvName.setText(arrayLang.get(position).getName());
                ((languageHolder) holder).tvLevel.setText(arrayLang.get(position).getLevel());
            }else if (type == 3) {
                ((schoolHolder) holder).tvSName.setText(arraySchool.get(position).getsName());
                ((schoolHolder) holder).tvStart.setText(arraySchool.get(position).getsDate());
                ((schoolHolder) holder).tvStudy.setText(arraySchool.get(position).getStudy());
                ((schoolHolder) holder).tvGrade.setText(arraySchool.get(position).getGrade());
                ((schoolHolder) holder).tvDegree.setText(arraySchool.get(position).getDegree());
            }
        }

        @Override
        public int getItemCount() {
            if (type == 0) {
                return arrayAccomplist.size();
            }else if (type == 1) {
                return arrayRef.size();
            }else if (type == 2) {
                return arrayLang.size();
            }else if (type == 3) {
                return arraySchool.size();
            }
            else {
                return arrayAccomplist.size();
            }
        }

    }

    private class accomplishHolder extends RecyclerView.ViewHolder{


        private TextView tvTitle,tvDate,tvDes;
        public accomplishHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.txtAccTitle);
            tvDate = (TextView) itemView.findViewById(R.id.txtAccDate);
            tvDes = (TextView) itemView.findViewById(R.id.txtAccDescription);
        }
    }

    private class referenceHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvTitle,tvTel,tvEmail,tvCompany;
        public referenceHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtRefName);
            tvTitle = (TextView) itemView.findViewById(R.id.txtRefTitle);
            tvTel = (TextView) itemView.findViewById(R.id.txtRefTel);
            tvEmail = (TextView) itemView.findViewById(R.id.txtRefEmail);
            tvCompany = (TextView) itemView.findViewById(R.id.txtRefCompany);
        }
    }

    private class languageHolder extends RecyclerView.ViewHolder {

        private TextView tvName,tvLevel;


        public languageHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.txtLangName);
            tvLevel = (TextView) itemView.findViewById(R.id.txtLangLevel);
        }
    }

    private class schoolHolder extends RecyclerView.ViewHolder {

        private TextView tvSName,tvStart,tvEnd,tvStudy,tvGrade,tvDegree;


        public schoolHolder(View itemView) {
            super(itemView);

            tvSName = (TextView) itemView.findViewById(R.id.txtSchoolName);
            tvStart = (TextView) itemView.findViewById(R.id.txtSchoolStart);
            tvEnd = (TextView) itemView.findViewById(R.id.txtSchoolEnd);
            tvStudy = (TextView) itemView.findViewById(R.id.txtSchoolStudy);
            tvGrade = (TextView) itemView.findViewById(R.id.txtSchoolGrade);
            tvDegree = (TextView) itemView.findViewById(R.id.txtSchoolDegree);
        }
    }

    private class AccomplishModel {

        String title,date,des;

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getDes() {
            return des;
        }

    }// accomplish model

    private  class ReferenceModel {

        String name,title,tel,email,com;

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getTel() {
            return tel;
        }

        public String getEmail() {
            return email;
        }

        public String getCom() {
            return com;
        }
    }

    private  class LanguageModel {

        String name,level;

        public String getName() {
            return name;
        }

        public String getLevel() {
            return level;
        }
    }

    private class SchoolModel {
        String sName,sDate,study,grade,degree;

        public String getsName() {
            return sName;
        }

        public String getsDate() {
            return sDate;
        }

        public String getStudy() {
            return study;
        }

        public String getGrade() {
            return grade;
        }

        public String getDegree() {
            return degree;
        }
    }

}
