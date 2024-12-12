package com.app.rkvmoneyrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.databinding.HistoryTxnAdapterNewBinding;
import com.app.rkvmoneyrecharge.databinding.TopupHistoryAdapterBinding;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.Datum;
import com.app.rkvmoneyrecharge.models.topup_history_model.Dynamic;

import java.util.ArrayList;
import java.util.List;

public class TopupHistoryAdapter extends RecyclerView.Adapter<TopupHistoryAdapter.Holder>{
    String type ;
    Context context ;
    List<Dynamic> list = new ArrayList<>() ;
    CommonInterface commonInterface ;

    public TopupHistoryAdapter(String type , Context context , List<Dynamic> list, CommonInterface commonInterface) {
        this.type = type;
        this.context = context;
        this.list = list;
        this.commonInterface = commonInterface;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopupHistoryAdapterBinding
         binding  = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.topup_history_adapter, parent, false);
        return new Holder(binding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        try {
            holder.binding.setModel(list.get(position)) ;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder {
        TopupHistoryAdapterBinding binding ;
        public Holder(@NonNull TopupHistoryAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
        }
    }
}
