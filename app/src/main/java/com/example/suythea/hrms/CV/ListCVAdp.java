package com.example.suythea.hrms.CV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.Interfaces.List_CV_And_Job_Interface;
import com.example.suythea.hrms.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVAdp extends ArrayAdapter<ListCVModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;
    List_CV_And_Job_Interface listCV_AndJob_interface;

    public ListCVAdp(Context _con, int _res, ArrayList<ListCVModel>listModels, MainCV _me){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listCV_AndJob_interface = (List_CV_And_Job_Interface) _me;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListCVHolder listCVHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listCVHolder = new ListCVHolder();
            listCVHolder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfileListCV);
            listCVHolder.txtFName = (TextView)convertView.findViewById(R.id.txtFNameListCV);
            listCVHolder.txtLName = (TextView)convertView.findViewById(R.id.txtLNameListCV);
            listCVHolder.txtPostedDate = (TextView)convertView.findViewById(R.id.txtPostedDateListCV);
            listCVHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleListCV);
            convertView.setTag(listCVHolder);

        }
        else {
            listCVHolder = (ListCVHolder)convertView.getTag();
        }

        ListCVModel listCVModel = getItem(position);

        listCVHolder.txtFName.setText("First Name : " + listCVModel.getfName());
        listCVHolder.txtLName.setText("Last Name : " + listCVModel.getlName());
        listCVHolder.txtTitle.setText(listCVModel.getTitle());
        listCVHolder.txtPostedDate.setText("Posted Date : " + listCVModel.getPostedDate());

        Picasso.with(context)
                .load("http://bongnu.khmerlabs.com/profile_images/" + listCVModel.getUid() + ".jpg")
                .placeholder(context.getResources().getIdentifier("no_profile","mipmap",context.getPackageName()))
                .into(listCVHolder.imgProfile, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

        if (position == MainCV.lisData.size() - 1){
            listCV_AndJob_interface.cameLastIndex();
        }

        return convertView;
    }

}
