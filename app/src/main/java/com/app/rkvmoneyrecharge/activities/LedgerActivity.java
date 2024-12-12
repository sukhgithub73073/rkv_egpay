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
import com.app.rkvmoneyrecharge.adapters.HistoryTxnAdapter;
import com.app.rkvmoneyrecharge.adapters.LadgerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityLedgerBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.TransactionHistoryModel;
import com.app.rkvmoneyrecharge.models.legder_model.Datum;
import com.app.rkvmoneyrecharge.models.legder_model.LedgerModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
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

public class LedgerActivity extends BaseActivity {
    ActivityLedgerBinding binding;
    LoginModel loginModel;
    List<Datum> list = new ArrayList<>();
    LadgerAdapter ladgerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ledger);
        loginModel = Utility.getLoginUser(getApplicationContext());
        setHistoryTxnAdapter();
        getLedgerReqReport();
        binding.back.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });

    }

    public void getLedgerReqReport() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        Map<String, String> reqMap = new HashMap<>();
        map.put("userid", "" + loginModel.getDynamic().getUserid());
        map.put("mobileno", "");
        map.put("date", Utility.getTodayDate());
        map.put("transactionid", "");
        map.put("transactionstatus", "");
        map.put("token", AppData.token);


        FLog.w("Login>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());
        try {
            reqMap = EncDecRepository.getEncryption(map);
            FLog.w("Login>>>>>", "reqMap>>>>>>>>>>>>>>>>>>>>" + reqMap.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/LedgerReport", reqMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    FLog.w("getTransactionHistory>>>", "onResponse:" + new Gson().toJson(response.body()));
                    globalLoader.dismissLoader();


                    String body = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("getTransactionHistory>>>>>", ">>>>>>>>>>>>>>>>>>>>" + body);
                    LedgerModel ledgerModel = new Gson().fromJson(body, LedgerModel.class);
                    if (ledgerModel.getData().size() > 0) {
                        list.addAll(ledgerModel.getData());
                    }
                    ladgerAdapter.notifyDataSetChanged();
                    binding.noResult.setVisibility(list.size() == 0 ? View.VISIBLE : View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {

            }
        });


    }

    private void setHistoryTxnAdapter() {
        ladgerAdapter = new LadgerAdapter(list, getApplicationContext());
        binding.setLedgerAdapter(ladgerAdapter);


    }
}