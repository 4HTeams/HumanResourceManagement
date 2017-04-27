package com.example.suythea.hrms.PostCV;

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
public class ListACCCAdp extends ArrayAdapter<ListPostCVModel> {

    Context context;
    int resource;
    LayoutInflater layoutInflater;

    public ListACCCAdp(Context _con, int _res, ArrayList<ListPostCVModel>listModels){

        super(_con,_res,listModels);

        context = _con;
        resource = _res;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListACCCHolder listACCCHolder;

        if (convertView == null){

            convertView = layoutInflater.inflate(this.resource,null);
            listACCCHolder = new ListACCCHolder();
            listACCCHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleListACCC);
            listACCCHolder.txtDate = (TextView)convertView.findViewById(R.id.txtDateListACCC);
            convertView.setTag(listACCCHolder);

        }
        else {
            listACCCHolder = (ListACCCHolder)convertView.getTag();
        }

        ListPostCVModel listPostCVModel = getItem(position);

        listACCCHolder.txtTitle.setText(listPostCVModel.getTitle());
        listACCCHolder.txtDate.setText(listPostCVModel.getDate());

        return convertView;
    }

}
