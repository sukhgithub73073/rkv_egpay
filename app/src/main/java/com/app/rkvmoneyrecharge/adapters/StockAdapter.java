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
import com.app.rkvmoneyrecharge.databinding.StockAdapterNewBinding;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.Datum;
import com.app.rkvmoneyrecharge.models.stock_model.Dynamic;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.Holder>{
    List<Dynamic> list = new ArrayList<>() ;

    public StockAdapter(List<Dynamic> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StockAdapterNewBinding binding  = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.stock_adapter_new, parent, false);
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
        StockAdapterNewBinding binding ;
        public Holder(@NonNull StockAdapterNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
        }
    }
}
