package com.app.rkvmoneyrecharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.rkvmoneyrecharge.models.legder_model.Datum;

import java.util.ArrayList;
import java.util.List;
import com.app.rkvmoneyrecharge.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.cachapa.expandablelayout.ExpandableLayout;

public class LadgerAdapter extends RecyclerView.Adapter<LadgerAdapter.Holder> {
    List<Datum> list = new ArrayList<>() ;

    Context context ;
    public LadgerAdapter(List<Datum> list , Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ladger_adapter_item , parent , false)) ;
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        try {
            holder.txt_date.setText("" + list.get(position).getDate()) ;
            holder.txn_cur_act.setText("₹ " + list.get(position).getBalance()) ;
            holder.txn_id.setText("" + list.get(position).getReferenceid()) ;
            holder.remark.setText("" + list.get(position).getDescription()) ;

            String status = "CREDIT" ;
            String req_amt = "0.0" ;

            if (list.get(position).getType().equalsIgnoreCase("Purchase")){
                holder.txt_status.setTextColor(context.getColor(R.color.red)) ;
                holder.txt_req_amt.setTextColor(context.getColor(R.color.red)) ;
                status = "DEBIT" ;
                req_amt = "- ₹ " + list.get(position).getAmount() ;
            }else{
                holder.txt_status.setTextColor(context.getColor(R.color.green)) ;
                holder.txt_req_amt.setTextColor(context.getColor(R.color.green)) ;

                req_amt = "+ ₹ " + list.get(position).getAmount() ;
            }
            holder.txt_status.setText("" + status) ;
            holder.txt_req_amt.setText("" + req_amt) ;

            holder.exp_opening.setText("Opening Balance : ₹ " +list.get(position).getBalance() ) ;
            holder.exp_req_amt.setText("Request Amount : ₹ " +list.get(position).getAmount() ) ;
            holder.exp_commision.setText("Commission : ₹ 00" ) ;
            holder.exp_closing.setText("Closing Balance : ₹ " +list.get(position).getBalance() ) ;

            holder.itemView.setOnClickListener(v->{
                holder.expandable_layout.setExpanded(!holder.expandable_layout.isExpanded());
                int res = R.drawable.plus_new ;
                if (holder.expandable_layout.isExpanded()){
                    res = R.drawable.minus_new ;
                }
                Glide.with(context)
                        .load(res)
                        .apply(new RequestOptions().placeholder(R.drawable.plus_new))
                        .into(holder.iv_button)   ;

            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return list.size() ;
    }
    class Holder extends RecyclerView.ViewHolder {
        TextView txt_date , txn_cur_act ,txn_id ,remark ,txt_req_amt ,txt_status ,
        exp_opening , exp_req_amt,exp_commision,exp_closing ;
                ;
        ExpandableLayout expandable_layout ;
        ImageView iv_button ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            exp_opening = itemView.findViewById(R.id.exp_opening) ;
            exp_req_amt = itemView.findViewById(R.id.exp_req_amt) ;
            exp_commision = itemView.findViewById(R.id.exp_commision) ;
            exp_closing = itemView.findViewById(R.id.exp_closing) ;
            iv_button = itemView.findViewById(R.id.iv_button) ;
            expandable_layout = itemView.findViewById(R.id.expandable_layout) ;
            txt_date = itemView.findViewById(R.id.txt_date) ;
            txn_cur_act = itemView.findViewById(R.id.txn_cur_act) ;
            txn_id = itemView.findViewById(R.id.txn_id) ;
            remark = itemView.findViewById(R.id.remark) ;
            txt_req_amt = itemView.findViewById(R.id.txt_req_amt) ;
            txt_status = itemView.findViewById(R.id.txt_status) ;
        }
    }
}
