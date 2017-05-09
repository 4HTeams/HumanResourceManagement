package com.example.suythea.hrms.ViewOwnCV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suythea.hrms.R;

import java.util.ArrayList;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListViewOwnCVAdp extends ArrayAdapter<ListViewOwnCVModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;

    public ListViewOwnCVAdp(Context _con, int _res, ArrayList<ListViewOwnCVModel>listModels){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewOwnCVHolder listViewOwnCVHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listViewOwnCVHolder = new ListViewOwnCVHolder();
            listViewOwnCVHolder.txtFName = (TextView)convertView.findViewById(R.id.txtFNameOwnCV);
            listViewOwnCVHolder.txtLName = (TextView)convertView.findViewById(R.id.txtLNameOwnCV);
            listViewOwnCVHolder.txtPostedDate = (TextView)convertView.findViewById(R.id.txtDateOwnCV);
            listViewOwnCVHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleOwnCV);
            convertView.setTag(listViewOwnCVHolder);

        }
        else {
            listViewOwnCVHolder = (ListViewOwnCVHolder)convertView.getTag();
        }

        ListViewOwnCVModel listViewOwnCVModel = getItem(position);

        listViewOwnCVHolder.txtFName.setText(listViewOwnCVModel.getfName());
        listViewOwnCVHolder.txtLName.setText(listViewOwnCVModel.getlName());
        listViewOwnCVHolder.txtPostedDate.setText(listViewOwnCVModel.getpDate());
        listViewOwnCVHolder.txtTitle.setText(listViewOwnCVModel.getTitle());

        return convertView;
    }

}
