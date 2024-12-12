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
import com.app.rkvmoneyrecharge.databinding.ActivityUserListBinding;

public class UserListActivity extends BaseActivity {
    ActivityUserListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

    }
}