package com.example.suythea.hrms.ViewOwnJob;

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
public class ListViewOwnJobAdp extends ArrayAdapter<ListViewOwnJobModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;

    public ListViewOwnJobAdp(Context _con, int _res, ArrayList<ListViewOwnJobModel>listModels){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewOwnJobHolder listViewOwnJobHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listViewOwnJobHolder = new ListViewOwnJobHolder();
            listViewOwnJobHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleOwnJob);
            listViewOwnJobHolder.txtExp = (TextView)convertView.findViewById(R.id.txtExpOwnJob);
            listViewOwnJobHolder.txtSalary = (TextView)convertView.findViewById(R.id.txtSalaryOwnJob);
            listViewOwnJobHolder.txtDeadline = (TextView)convertView.findViewById(R.id.txtDeadlineOwnJob);
            convertView.setTag(listViewOwnJobHolder);

        }
        else {
            listViewOwnJobHolder = (ListViewOwnJobHolder)convertView.getTag();
        }

        ListViewOwnJobModel listViewOwnJobModel = getItem(position);

        listViewOwnJobHolder.txtTitle.setText(listViewOwnJobModel.getTitle());
        listViewOwnJobHolder.txtExp.setText(listViewOwnJobModel.getExp());
        listViewOwnJobHolder.txtSalary.setText(listViewOwnJobModel.getSalary());
        listViewOwnJobHolder.txtDeadline.setText(listViewOwnJobModel.getDeadline());

        return convertView;
    }

}
