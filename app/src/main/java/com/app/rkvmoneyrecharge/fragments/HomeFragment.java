package com.app.rkvmoneyrecharge.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.activities.CommingSoonActivity;
import com.app.rkvmoneyrecharge.activities.HistoryActivity;
import com.app.rkvmoneyrecharge.activities.LedgerActivity;
import com.app.rkvmoneyrecharge.activities.LoginActivity;
import com.app.rkvmoneyrecharge.activities.MainActivity;
import com.app.rkvmoneyrecharge.activities.StockActivity;
import com.app.rkvmoneyrecharge.activities.bbps.ElectricityActivity;
import com.app.rkvmoneyrecharge.activities.bbps.PrepaidActivity;
import com.app.rkvmoneyrecharge.activities.recharges.DTHActivity;
import com.app.rkvmoneyrecharge.activities.reports.TopupHistoryActivity;
import com.app.rkvmoneyrecharge.adapters.ViewPagerAdapter;
import com.app.rkvmoneyrecharge.databinding.FragmentHomeBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.balance_model.BalanceModel;
import com.app.rkvmoneyrecharge.models.banner_model.BannerModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileReportsModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.CommonDB;
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

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    LoginModel loginModel;
    int CURRENT_POS = 0;
    List<Fragment> fragmentList = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        loginModel = Utility.getLoginUser(getContext());
        binding.txtName.setText("" + loginModel.getDynamic().getName());
        binding.txtType.setText("" + loginModel.getDynamic().getLogintype());
        binding.txtMobile.setText("" + loginModel.getDynamic().getMobileno());
        getBanner();
        getBalance();
        manageClickListeners();
        return binding.getRoot();
    }

    private void getBanner() {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("getBanner>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetBanner", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("getBanner>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    BannerModel bannerModel = new Gson().fromJson(loginBody, BannerModel.class);
                    fragmentList.clear();
                    for (int i = 0; i < bannerModel.getDynamic().size(); i++) {
                        fragmentList.add(new ImageFragment(bannerModel.getDynamic().get(i).getUrl() + "?s=" + System.currentTimeMillis()));
                    }
                    binding.imageViewPager.setAdapter(new ViewPagerAdapter(getActivity(), fragmentList));
                    setScollView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
            }
        });
    }

    private void setScollView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CURRENT_POS == fragmentList.size()) {
                    CURRENT_POS = 0;
                }
                binding.imageViewPager.setCurrentItem(CURRENT_POS);
                CURRENT_POS = CURRENT_POS + 1;
                setScollView();
            }
        }, 3000);

    }

    private void manageClickListeners() {

        binding.txtBal.setOnClickListener(v -> {
            getBalance();
        });

        binding.txtStock.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), StockActivity.class));

        });
        binding.linMobile.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PrepaidActivity.class));

        });
        binding.dth.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DTHActivity.class));
        });

        binding.electricity.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Electricity";
            startActivity(new Intent(getContext(), ElectricityActivity.class));
        });
        binding.postpaidBill.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Postpaid Bill";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });

        binding.linGas.setOnClickListener(v -> {
            AppData.commingSoonTitle = "LPG Gas";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });
        binding.googlePlay.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Google Play Code";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });
        binding.fastag.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Fastag";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });

        binding.amazone.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Amazone Gift Card";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });

        binding.linLedgerReport.setOnClickListener(v -> {
            ProfileReportsModel profileReportsModel = new ProfileReportsModel("Api/LedgerReport", "Ledger Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class));
            new CommonDB(getContext()).putString("REPORT_JSON", new Gson().toJson(profileReportsModel));
            startActivity(new Intent(getContext(), LedgerActivity.class));
        });
        binding.historyRecharge.setOnClickListener(v -> {
            ProfileReportsModel profileReportsModel = new ProfileReportsModel("Api/RechargeReport", "Recharge Report", R.drawable.logo, new Intent(getContext(), HistoryActivity.class));
            new CommonDB(getContext()).putString("REPORT_JSON", new Gson().toJson(profileReportsModel));
            startActivity(new Intent(getContext(), HistoryActivity.class));
        });
        binding.historyTopup.setOnClickListener(v -> {
            ProfileReportsModel profileReportsModel = new ProfileReportsModel("Api/TopUpReport", "Wallet Topup Report", R.drawable.logo, new Intent(getContext(), HistoryActivity.class));
            new CommonDB(getContext()).putString("REPORT_JSON", new Gson().toJson(profileReportsModel));
            startActivity(new Intent(getContext(), TopupHistoryActivity.class));
        });
        binding.historyRefund.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Refund Report";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });
        binding.historyCommission.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Commision Report";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });
        binding.historyRefferal.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Refferal Report";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });
        binding.historyPurchase.setOnClickListener(v -> {
            AppData.commingSoonTitle = "Purchase Report";
            startActivity(new Intent(getContext(), CommingSoonActivity.class));
        });


    }

    private void getBalance() {
        binding.txtBal.setVisibility(View.GONE);
        binding.progressBal.setVisibility(View.VISIBLE);
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("GetBalance>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetBalance", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    binding.txtBal.setVisibility(View.VISIBLE);
                    binding.progressBal.setVisibility(View.GONE);
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("getBanner>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    BalanceModel balanceModel = new Gson().fromJson(loginBody, BalanceModel.class);
                    if (balanceModel.getStatus()) {
                        binding.txtBal.setText("₹ " + balanceModel.getDynamic().getBuyerBalance());
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                binding.txtBal.setText("View Balance");
//                            }
//                        }, 3000);
                    }


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