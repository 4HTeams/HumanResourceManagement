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

import com.example.suythea.hrms.Interfaces.ListCV_Interface;
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
    ListCV_Interface listCV_interface;

    public ListCVAdp(Context _con, int _res, ArrayList<ListCVModel>listModels, MainCV _me){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listCV_interface = (ListCV_Interface) _me;
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

        listCVHolder.txtFName.setText(listCVModel.getfName());
        listCVHolder.txtLName.setText(listCVModel.getlName());
        listCVHolder.txtTitle.setText(listCVModel.getTitle());
        listCVHolder.txtPostedDate.setText(listCVModel.getPostedDate());

        if (position == MainCV.lisData.size() - 1){
            listCV_interface.cameLastIndex();
        }

        return convertView;
    }

}
