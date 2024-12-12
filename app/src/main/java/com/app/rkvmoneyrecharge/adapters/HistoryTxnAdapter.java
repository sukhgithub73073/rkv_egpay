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
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.Datum;

import java.util.ArrayList;
import java.util.List;

public class HistoryTxnAdapter extends RecyclerView.Adapter<HistoryTxnAdapter.Holder> {
    String type;
    Context context;
    List<Datum> list = new ArrayList<>();
    CommonInterface commonInterface;

    public HistoryTxnAdapter(String type, Context context, List<Datum> list, CommonInterface commonInterface) {
        this.type = type;
        this.context = context;
        this.list = list;
        this.commonInterface = commonInterface;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryTxnAdapterNewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.history_txn_adapter_new, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        try {
            holder.binding.setModel(list.get(position));
            if (list.get(position).getStatus().equalsIgnoreCase("success")) {
                holder.binding.status.setBackgroundColor(context.getColor(R.color.green));
            }else if (list.get(position).getStatus().equalsIgnoreCase("pending")) {
                holder.binding.status.setBackgroundColor(context.getColor(R.color.pending_color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        HistoryTxnAdapterNewBinding binding;

        public Holder(@NonNull HistoryTxnAdapterNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
