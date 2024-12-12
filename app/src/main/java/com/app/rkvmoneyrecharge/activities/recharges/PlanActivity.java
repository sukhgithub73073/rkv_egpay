package com.app.rkvmoneyrecharge.activities.recharges;

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
import com.app.rkvmoneyrecharge.adapters.PlanAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityPlanBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.roffer_model.RofferModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanActivity extends BaseActivity {
    ActivityPlanBinding binding;
    List<com.app.rkvmoneyrecharge.models.roffer_model.Response> planList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan);
        getROfferApi();
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }

    private void getROfferApi() {

        globalLoader.showLoader();
        Map<String, String> encMap = EncDecRepository.getEncryption(AppData.map);
        FLog.w("getROfferApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/ROffer", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("getROfferApi>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);

                    RofferModel historyModel = new Gson().fromJson(loginBody, RofferModel.class);
                    planList.clear();
                    if (historyModel.getDynamic() == null) {
                        binding.noResult.setVisibility(View.VISIBLE);
                    } else {
                        binding.noResult.setVisibility(View.GONE);
                        planList.addAll(historyModel.getDynamic().getResponse());
                        FLog.w("getROfferApi>>>>>", "planList" + planList.size());
                        setPlanAdapters();
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
    void setPlanAdapters() {
        PlanAdapter planAdapter = new PlanAdapter(planList, new CommonInterface() {
            @Override
            public void onItemClickedOnPosition(int position) {
                AppData.selectedAmount = planList.get(position).getPrice() ;
                finish() ;
               // binding.etAmount.setText("" + planList.get(position).getPrice());
            }
        });
        binding.setPlanAdapter(planAdapter);
    }
}