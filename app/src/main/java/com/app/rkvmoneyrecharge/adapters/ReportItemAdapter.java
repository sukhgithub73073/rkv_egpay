package com.app.rkvmoneyrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.interfaces.ProfileItemInterface;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileReportsModel;

import java.util.ArrayList;
import java.util.List;

public class ReportItemAdapter extends RecyclerView.Adapter<ReportItemAdapter.Holder> {

    List<ProfileReportsModel> list = new ArrayList<>() ;
    Context context ;
    ProfileItemInterface commonInterface ;
//tesr
    public ReportItemAdapter(List<ProfileReportsModel> list, Context context, ProfileItemInterface commonInterface) {
        this.list = list;
        this.context = context;
        this.commonInterface = commonInterface;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_reports_adapter_item , parent , false));
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(" "+ list.get(position).getName());
//        Glide.with(context)
//                .load(list.get(position).getIcon())
//                .apply(new RequestOptions().placeholder(R.drawable.logo))
//                .into(holder.iv_icon)  ;
        holder.itemView.setOnClickListener(v->{
            commonInterface.onItemClicked(list.get(position)) ;
        });
    }
    @Override
    public int getItemCount() {
        return list.size() ;
    }
    class Holder extends RecyclerView.ViewHolder {
        TextView name ;
        ImageView iv_icon ;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name) ;
            iv_icon = itemView.findViewById(R.id.iv_icon) ;
        }
    }
}
