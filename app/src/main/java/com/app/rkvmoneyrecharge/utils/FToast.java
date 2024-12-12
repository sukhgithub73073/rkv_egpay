package com.app.rkvmoneyrecharge.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.rkvmoneyrecharge.R;


public class FToast {

    public static int  LENGTH_SHORT = 1 ;
    public static int  LENGTH_LONG = 1 ;

    public static Toast makeText(Context context , String message , int duration){
        View view = LayoutInflater.from(context).inflate(R.layout.f_toast, null);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view) ;
        TextView txt_message = view.findViewById(R.id.txt_message) ;
        txt_message.setText("" + message) ;
        return  toast ;
    }


}
