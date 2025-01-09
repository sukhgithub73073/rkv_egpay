package com.app.rkvmoneyrecharge.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityLoginBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.app.rkvmoneyrecharge.utils.GlobalLoader;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


//        //if (BuildConfig.DEBUG) {
//        if (true) {
//            binding.etMobile.setText("9521821501");
//            binding.etPassword.setText("F073F261");
//        }

        binding.txtForgot.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ForgotActivity.class));
        });

        binding.txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MobileActivity.class));
        });

        binding.login.setOnClickListener(v -> {
            hitLoginApi();
        });
    }

    private void hitLoginApi() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("UserId", binding.etMobile.getText().toString());
        map.put("Password", binding.etPassword.getText().toString());
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("Login>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().Login(encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("Login>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    CommonModel commonModel = new Gson().fromJson(loginBody, CommonModel.class);
                    FToast.makeText(getApplicationContext(), commonModel.getMessage(), FToast.LENGTH_SHORT).show();
                    if (commonModel.getStatus()) {
                        LoginModel loginModel = new Gson().fromJson(loginBody, LoginModel.class);
                        AppData.token = loginModel.getDynamic().getToken() ;
                        Utility.updateUserData(getApplicationContext(), loginModel);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

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