package com.app.rkvmoneyrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.databinding.HistoryTxnAdapterNewBinding;
import com.app.rkvmoneyrecharge.databinding.RetailerAdapterItemBinding;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.retailer_model.Dynamic;

import java.util.ArrayList;
import java.util.List;

public class RetailerAdapter extends RecyclerView.Adapter<RetailerAdapter.Holder> {
    Context context;
    List<Dynamic> list = new ArrayList<>();

    public RetailerAdapter(Context context, List<Dynamic> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RetailerAdapterItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.retailer_adapter_item, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        try {
            holder.binding.setModel(list.get(position));
            if (list.get(position).getAdminStatus()) {
                holder.binding.status.setBackgroundColor(context.getColor(R.color.green));
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
        RetailerAdapterItemBinding binding;

        public Holder(@NonNull RetailerAdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
