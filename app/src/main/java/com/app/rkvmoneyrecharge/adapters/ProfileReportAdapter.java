package com.app.rkvmoneyrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.databinding.ProfileReportAdapterItemBinding;
import com.app.rkvmoneyrecharge.interfaces.ProfileItemInterface;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileReportAdapter extends RecyclerView.Adapter<ProfileReportAdapter.Holder> {
    List<ProfileModel> list = new ArrayList<>() ;
    ProfileReportAdapterItemBinding binding ;
    Context context ;
    ProfileItemInterface commonInterface ;

    public ProfileReportAdapter(List<ProfileModel> list, Context context, ProfileItemInterface commonInterface) {
        this.list = list;
        this.context = context;
        this.commonInterface = commonInterface;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding  = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.profile_report_adapter_item, parent, false);
        return new Holder(binding) ;
        //return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_report_adapter_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
       holder.binding.setModel(list.get(position)) ;
       holder.binding.setReportAdapter(new ReportItemAdapter(list.get(position).list , context , commonInterface));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ProfileReportAdapterItemBinding binding ;
        public Holder(@NonNull ProfileReportAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
        }
    }

}
