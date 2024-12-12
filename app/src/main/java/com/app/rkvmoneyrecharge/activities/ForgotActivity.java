package com.app.rkvmoneyrecharge.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityForgotBinding;
import com.app.rkvmoneyrecharge.databinding.ActivityLoginBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends BaseActivity {
    private ActivityForgotBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);
       // binding.etMobile.setText("7307372923");

        binding.txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext() , LoginActivity.class));
        });

        binding.submit.setOnClickListener(v -> {
            hitForgotApi();
        });
    }

    private void hitForgotApi() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("UserId", binding.etMobile.getText().toString());
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("hitForgotApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().ForgPassword(encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("hitForgotApi>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    CommonModel userModel = new Gson().fromJson(loginBody, CommonModel.class);
                    FToast.makeText(getApplicationContext(), userModel.getMessage(), FToast.LENGTH_SHORT).show();
                    if (userModel.getStatus()) {
                       startActivity(new Intent(getApplicationContext() , LoginActivity.class));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                globalLoader.dismissLoader();
            }
        });
    }
}