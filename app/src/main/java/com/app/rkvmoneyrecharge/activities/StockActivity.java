package com.app.rkvmoneyrecharge.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.StockAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityStockBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.balance_model.BalanceModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.stock_model.Dynamic;
import com.app.rkvmoneyrecharge.models.stock_model.StockModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockActivity extends BaseActivity {
    ActivityStockBinding binding ;
    LoginModel loginModel ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_stock) ;
        loginModel = Utility.getLoginUser(getApplicationContext()) ;
        GetStockAvailable();
        binding.back.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });

    }



    private void GetStockAvailable() {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("GetStockAvailable>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetStockAvailable", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("GetStockAvailable>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    StockModel stockModel = new Gson().fromJson(loginBody, StockModel.class);
                    binding.noResult.setVisibility(stockModel.getDynamic().isEmpty() ? View.VISIBLE : View.GONE);
                    binding.setStockAdapter(new StockAdapter(stockModel.getDynamic()));

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