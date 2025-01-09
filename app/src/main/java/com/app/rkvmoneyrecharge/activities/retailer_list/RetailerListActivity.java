package com.app.rkvmoneyrecharge.activities.retailer_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.RetailerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityRetailerListBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.TransactionHistoryModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.retailer_model.RetailerModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.GlobalLoader;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerListActivity extends BaseActivity {
    ActivityRetailerListBinding binding;
    LoginModel loginModel;
    GlobalLoader globalLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_list);
        loginModel = Utility.getLoginUser(getApplicationContext());
        globalLoader = new GlobalLoader(RetailerListActivity.this);
        ReferenceUserList();
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RetailerRegisterActivity.class));

        });

    }


    public void ReferenceUserList() {

        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        Map<String, String> reqMap = new HashMap<>();

        map.put("userid", "" + loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        FLog.w("ReferenceUserList>>>>>", "map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        try {
            reqMap = EncDecRepository.getEncryption(map);
            FLog.w("ReferenceUserList>>>>>", "reqMap>>>>>>>>>>>>>>>>>>>>" + reqMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/ReferenceUserList", reqMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    FLog.w("ReferenceUserList>>>", "onResponse:" + new Gson().toJson(response.body()));
                    globalLoader.dismissLoader();
                    String body = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("ReferenceUserList>>>>>", ">>>>>>>>>>>>>>>>>>>>" + body);
                    RetailerModel retailerModel = new Gson().fromJson(body, RetailerModel.class);
                    binding.setRetailerAdapter(new RetailerAdapter(getApplicationContext(), retailerModel.getDynamic()));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {

            }
        });


    }

}