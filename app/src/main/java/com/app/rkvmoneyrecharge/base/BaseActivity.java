package com.app.rkvmoneyrecharge.base;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.app.rkvmoneyrecharge.utils.GlobalLoader;

import org.jetbrains.annotations.Nullable;

public class BaseActivity extends AppCompatActivity {
    public static GlobalLoader globalLoader ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalLoader =new GlobalLoader(this) ;

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }


}