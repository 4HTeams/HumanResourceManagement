package com.example.suythea.hrms.ViewCV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suythea.hrms.R;

import java.util.ArrayList;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListViewCVAdp extends ArrayAdapter<ListViewCVModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;

    public ListViewCVAdp(Context _con, int _res, ArrayList<ListViewCVModel>listModels){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewCVHolder listViewCVHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listViewCVHolder = new ListViewCVHolder();
            listViewCVHolder.txtFName = (TextView)convertView.findViewById(R.id.txtFNameOwnCV);
            listViewCVHolder.txtLName = (TextView)convertView.findViewById(R.id.txtLNameOwnCV);
            listViewCVHolder.txtPostedDate = (TextView)convertView.findViewById(R.id.txtDateOwnCV);
            listViewCVHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleOwnCV);
            convertView.setTag(listViewCVHolder);

        }
        else {
            listViewCVHolder = (ListViewCVHolder)convertView.getTag();
        }

        ListViewCVModel listViewCVModel = getItem(position);

        listViewCVHolder.txtFName.setText(listViewCVModel.getfName());
        listViewCVHolder.txtLName.setText(listViewCVModel.getlName());
        listViewCVHolder.txtPostedDate.setText(listViewCVModel.getpDate());
        listViewCVHolder.txtTitle.setText(listViewCVModel.getTitle());

        return convertView;
    }

}
