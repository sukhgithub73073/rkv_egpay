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
import com.app.rkvmoneyrecharge.models.ContactNumberModel;
import com.app.rkvmoneyrecharge.models.roffer_model.Response;

import java.util.ArrayList;
import java.util.List;

public class ContactNumberAdapter extends RecyclerView.Adapter<ContactNumberAdapter.Holder> {
    List<ContactNumberModel> list = new ArrayList<>() ;
    CommonInterface commonInterface ;

    public ContactNumberAdapter(List<ContactNumberModel> list , CommonInterface commonInterface) {
        this.list = list ;
        this.commonInterface = commonInterface ;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_number_adapter_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText("" + list.get(position).getName()) ;
        holder.mobile.setText("" + list.get(position).getMobile()) ;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonInterface.onItemClickedOnPosition(position) ;
            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size() ;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name  ,mobile ;
    public Holder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name) ;
        mobile = itemView.findViewById(R.id.mobile) ;
    }
}
}
