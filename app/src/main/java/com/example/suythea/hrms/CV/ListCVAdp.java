package com.example.suythea.hrms.CV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVAdp extends ArrayAdapter<ListCVModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;

    public ListCVAdp(Context _con, int _res, ArrayList<ListCVModel>listModels){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListCVHolder listCVHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listCVHolder = new ListCVHolder();
            listCVHolder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfileListCV);
            listCVHolder.txtName = (TextView)convertView.findViewById(R.id.txtNameListCV);
            listCVHolder.txtJob = (TextView)convertView.findViewById(R.id.txtJobListCV);
            listCVHolder.txtExper = (TextView)convertView.findViewById(R.id.txtExperListCV);
            listCVHolder.txtEx_Salary = (TextView)convertView.findViewById(R.id.txtE_SalaryListCV);
            listCVHolder.txtPostedDate = (TextView)convertView.findViewById(R.id.txtPostedDateListCV);
            convertView.setTag(listCVHolder);

        }
        else {
            listCVHolder = (ListCVHolder)convertView.getTag();
        }

        ListCVModel listCVModel = getItem(position);

        listCVHolder.txtName.setText(listCVModel.getName());
        listCVHolder.txtJob.setText(listCVModel.getJob());
        listCVHolder.txtExper.setText(listCVModel.getExperience());
        listCVHolder.txtEx_Salary.setText(listCVModel.getEx_salary());
        listCVHolder.txtPostedDate.setText(listCVModel.getPostedDate());

        return convertView;
    }

}
