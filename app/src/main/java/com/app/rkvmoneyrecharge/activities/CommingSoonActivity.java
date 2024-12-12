package com.app.rkvmoneyrecharge.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityCommingSoonBinding;
import com.app.rkvmoneyrecharge.utils.AppData;

public class CommingSoonActivity extends BaseActivity {

    ActivityCommingSoonBinding binding ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_comming_soon);
        binding.txtTitle.setText(AppData.commingSoonTitle) ;
        binding.back.setOnClickListener(v->{getOnBackPressedDispatcher().onBackPressed();});

    }
}