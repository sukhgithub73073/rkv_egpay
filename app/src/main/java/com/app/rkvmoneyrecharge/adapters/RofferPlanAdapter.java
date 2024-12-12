package com.app.rkvmoneyrecharge.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.Response;

import java.util.ArrayList;
import java.util.List;

public class RofferPlanAdapter extends RecyclerView.Adapter<RofferPlanAdapter.Holder> {
    List<Response> list = new ArrayList<>();
    CommonInterface commonInterface;

    public RofferPlanAdapter(List<Response> list, CommonInterface commonInterface) {
        this.list = list;
        this.commonInterface = commonInterface;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roffer_plan_adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_price.setText("\u20B9 " + list.get(position).getPrice());
        holder.txt_description.setText("" + list.get(position).getDescription());
        holder.validity.setText("Validity : " + list.get(position).getValidity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonInterface.onItemClickedOnPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txt_price ,validity, txt_description;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_price = itemView.findViewById(R.id.txt_price);
            validity = itemView.findViewById(R.id.validity);
            txt_description = itemView.findViewById(R.id.txt_description);
        }
    }
}
