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
import androidx.fragment.app.Fragment;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.ViewPagerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityRofferBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.fragments.MobilePlanFragment;
import com.app.rkvmoneyrecharge.models.check_plan_model.CheckPlanModel;
import com.app.rkvmoneyrecharge.models.roffer_model.RofferModel;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.RofferPlanModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RofferActivity extends BaseActivity {
    ActivityRofferBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_roffer);
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        checkPlan();

    }

    void checkPlan() {
        globalLoader.showLoader();
        String url = "https://rkvplan.in/api/checkPlan?userid=" + AppData.userid + "&token=" + AppData.tokenRKV
                + "&optnm=" + AppData.map.get("Operator") + "&circlenm=" + AppData.map.get("state") + "&type=MOBILE";

        RetrofitClient.getRetrofitInstance().checkPlan(url).enqueue(new Callback<CheckPlanModel>() {
            @Override
            public void onResponse(Call<CheckPlanModel> call, Response<CheckPlanModel> response) {
                try {
                    globalLoader.dismissLoader();
                    FLog.w("checkPlan>>>>>", ">>>>>>>>>>>>>>>>>>>>" + new Gson().toJson(response.body()));
                    AppData.checkPlanModel = response.body();
                    if (!AppData.checkPlanModel.getDynamic().getResponse().isEmpty()) {
                        planDetails(AppData.checkPlanModel.getDynamic().getResponse().get(0).getName());
                    }
                    setTabberAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CheckPlanModel> call, Throwable t) {

            }
        });


    }

    private void setTabberAdapter() {
        for (int i = 0; i < AppData.checkPlanModel.getDynamic().getResponse().size(); i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(AppData.checkPlanModel.getDynamic().getResponse().get(i).getName()));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                planDetails(tab.getText().toString());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void planDetails(String name) {
        globalLoader.showLoader();

        String url = "https://rkvplan.in/api/planDetails?userid=" + AppData.userid + "&token=" + AppData.tokenRKV
                + "&optnm=" + AppData.map.get("Operator") + "&circlenm=" + AppData.map.get("state") + "&type=MOBILE&name=" + name;

        FLog.w("planDetails>>>>>", ">URLL>>>>>>>>>>>>>>>>>>>" + url) ;

        RetrofitClient.getRetrofitInstance().planDetails(url).enqueue(new Callback<RofferPlanModel>() {
            @Override
            public void onResponse(Call<RofferPlanModel> call, Response<RofferPlanModel> response) {
                globalLoader.dismissLoader();
                setViewPagger(response.body());
                FLog.w("planDetails>>>>>", ">>>>>>>>>>>>>>>>>>>>" + new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<RofferPlanModel> call, Throwable t) {
                globalLoader.dismissLoader();
            }
        });
    }

    void setViewPagger(RofferPlanModel rofferPlanModel) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MobilePlanFragment(rofferPlanModel.getDynamic().getResponse(), plan -> {
            AppData.selectedAmount = plan.getPrice();
            finish();

        }));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentList);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.setUserInputEnabled(false);
    }

}