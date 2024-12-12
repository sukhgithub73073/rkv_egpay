package com.app.rkvmoneyrecharge.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.models.company_info_model.StateModel;

import java.util.List;

public class StateSpinnerAdapter extends ArrayAdapter<StateModel> {

    LayoutInflater flater;

    public StateSpinnerAdapter(Activity context, int resouceId, List<StateModel> list){

        super(context,resouceId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        StateModel rowItem = getItem(position);

        StateSpinnerAdapter.viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {
            holder = new StateSpinnerAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.state_spinner_adapter_item, null, false);
            holder.txtTitle = rowview.findViewById(R.id.name);

            rowview.setTag(holder);
        }else{
            holder = (StateSpinnerAdapter.viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getText());

        return rowview;

        }

    public class viewHolder{
        TextView txtTitle;
        ImageView iv_image ;
    }
}