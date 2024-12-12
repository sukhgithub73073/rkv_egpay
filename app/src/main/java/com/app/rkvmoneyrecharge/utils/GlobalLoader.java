package com.app.rkvmoneyrecharge.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.app.rkvmoneyrecharge.R;


public class GlobalLoader {
    Activity activity ;
    Dialog dialog ;

    public GlobalLoader(Activity activity) {
        this.activity = activity;
    }


    public void showLoader(){
        dialog = new Dialog(activity) ;
        dialog.setContentView(R.layout.loader) ;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false) ;
        dialog.show() ;

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // finish();
                    // dialog.dismiss();
                }
                return true;
            }
        });
        // activity.moveTaskToBack(false) ;


    }


    public void dismissLoader(){
        dialog.dismiss() ;
    }

}
