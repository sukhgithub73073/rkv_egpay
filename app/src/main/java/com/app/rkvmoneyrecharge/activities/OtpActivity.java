package com.app.rkvmoneyrecharge.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityOtpBinding;
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

public class OtpActivity extends BaseActivity {
    ActivityOtpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        binding.submit.setOnClickListener(v -> {
            verifyOtpApi();

        });
        binding.back.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });

    }

    private void verifyOtpApi() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("mobileno", getIntent().getStringExtra("MobileNo"));
        map.put("otp", binding.etOtp.getText().toString());

        FLog.w("verifyOtpApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("verifyOtpApi>>>>>", "ENC---Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/VerifyOTP", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("verifyOtpApi>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    CommonModel userModel = new Gson().fromJson(loginBody, CommonModel.class);
                    FToast.makeText(getApplicationContext(), userModel.getMessage(), FToast.LENGTH_SHORT).show();
                    if (userModel.getStatus()) {
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class).putExtra("MobileNo", getIntent().getStringExtra("MobileNo")));
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