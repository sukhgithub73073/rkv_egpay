package com.app.rkvmoneyrecharge.activities;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.ViewPagerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.fragments.HomeFragment;
import com.app.rkvmoneyrecharge.fragments.ProfileFragment;
import com.app.rkvmoneyrecharge.fragments.QrFragment;
import com.app.rkvmoneyrecharge.models.CategoryOperatorModel.OperatorCategoriesModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.all_operator_model.AllOperatorModel;
import com.app.rkvmoneyrecharge.models.all_state_model.AllStateModel;
import com.app.rkvmoneyrecharge.models.banner_model.BannerModel;
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

public class MainActivity extends BaseActivity {
    List<Fragment> fragmentList = new ArrayList<>();
    ViewPager2 view_pager;
    ViewPagerAdapter viewPagerAdapter;
    //    ActivityMainBinding binding ;
    LinearLayout lin_home, lin_support, lin_profile, lin_wallet, lin_game;
    ImageView iv_home, iv_support, iv_profile, iv_wallet, iv_game;
    TextView txt_home, txt_support, txt_profile, txt_wallet, txt_game;
    LoginModel loginModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginModel = Utility.getLoginUser(getApplicationContext());
        GetStates();
        // GetOperators();
        // GetOperatorsDth();
        GetOperatorCategories();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new QrFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ProfileFragment());
        initViews();
        manageClicks();

    }

    private void GetStates() {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("rechargeRequestApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetStates", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("GetStates>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    AllStateModel allOperatorModel = new Gson().fromJson(loginBody, AllStateModel.class);
                    AppData.allStateList = allOperatorModel.getDynamic();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
            }
        });

    }


    private void GetOperators(String operatorcategory) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        map.put("operatorcategory", operatorcategory);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("rechargeRequestApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetOperators", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("GetOperators>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    AllOperatorModel allOperatorModel = new Gson().fromJson(loginBody, AllOperatorModel.class);
                    AppData.allOperatorList = allOperatorModel.getDynamic();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
            }
        });

    }

    private void GetOperatorsDth(String operatorcategory) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        map.put("operatorcategory", operatorcategory);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("GetOperatorsDth>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetOperators", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("GetOperatorsDth>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    AllOperatorModel allOperatorModel = new Gson().fromJson(loginBody, AllOperatorModel.class);
                    AppData.allOperatorListDTH = allOperatorModel.getDynamic();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
            }
        });

    }


    private void GetOperatorCategories() {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);
        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("GetOperatorCategories>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/GetOperatorCategories", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("GetOperatorCategories>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    OperatorCategoriesModel operatorCategoriesModel = new Gson().fromJson(loginBody, OperatorCategoriesModel.class);
                    for (int i = 0; i < operatorCategoriesModel.getDynamic().size(); i++) {
                        if (operatorCategoriesModel.getDynamic().get(i).getText().equalsIgnoreCase("DTH")) {
                            GetOperatorsDth(operatorCategoriesModel.getDynamic().get(i).getValue());
                        } else if (operatorCategoriesModel.getDynamic().get(i).getText().equalsIgnoreCase("Prepaid")) {
                            GetOperators(operatorCategoriesModel.getDynamic().get(i).getValue());
                        }
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


    private void manageClicks() {
        lin_home.setOnClickListener(v -> {
            navPositionClicked(0);
        });
        lin_wallet.setOnClickListener(v -> {
            navPositionClicked(1);

        });
        lin_game.setOnClickListener(v -> {
            navPositionClicked(2);

        });
        lin_support.setOnClickListener(v -> {
            navPositionClicked(3);

        });
        lin_profile.setOnClickListener(v -> {
            navPositionClicked(4);

        });
    }

    private void initViews() {
        lin_home = findViewById(R.id.lin_home);
        lin_wallet = findViewById(R.id.lin_wallet);
        lin_game = findViewById(R.id.lin_game);
        lin_support = findViewById(R.id.lin_support);
        lin_profile = findViewById(R.id.lin_profile);

        iv_home = findViewById(R.id.iv_home);
        iv_wallet = findViewById(R.id.iv_wallet);
        iv_game = findViewById(R.id.iv_game);
        iv_support = findViewById(R.id.iv_support);
        iv_profile = findViewById(R.id.iv_profile);

        txt_home = findViewById(R.id.txt_home);
        txt_wallet = findViewById(R.id.txt_wallet);
        txt_game = findViewById(R.id.txt_game);

        txt_support = findViewById(R.id.txt_support);
        txt_profile = findViewById(R.id.txt_profile);
        view_pager = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, fragmentList);
        view_pager.setAdapter(viewPagerAdapter);
        view_pager.setUserInputEnabled(false);

    }

    private void navPositionClicked(int pos) {

        txt_home.setTextColor(getColor(R.color.black));
        setTextViewDrawableColor(txt_home, R.color.black);

        txt_wallet.setTextColor(getColor(R.color.black));
        setTextViewDrawableColor(txt_wallet, R.color.black);

        txt_game.setTextColor(getColor(R.color.black));
        setTextViewDrawableColor(txt_game, R.color.black);

        txt_support.setTextColor(getColor(R.color.black));
        setTextViewDrawableColor(txt_support, R.color.black);

        txt_profile.setTextColor(getColor(R.color.black));
        setTextViewDrawableColor(txt_profile, R.color.black);


        iv_home.setColorFilter(getResources().getColor(R.color.black));
        iv_wallet.setColorFilter(getResources().getColor(R.color.black));
        iv_game.setColorFilter(getResources().getColor(R.color.black));
        iv_support.setColorFilter(getResources().getColor(R.color.black));
        iv_profile.setColorFilter(getResources().getColor(R.color.black));

        iv_home.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        iv_wallet.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        iv_game.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        iv_support.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
        iv_profile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));

        if (pos == 0) {
            txt_home.setTextColor(getColor(R.color.primary));
            setTextViewDrawableColor(txt_home, R.color.primary);
            iv_home.setColorFilter(getResources().getColor(R.color.primary));
            iv_home.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
        } else if (pos == 1) {
            txt_wallet.setTextColor(getColor(R.color.primary));
            setTextViewDrawableColor(txt_wallet, R.color.primary);
            iv_wallet.setColorFilter(getResources().getColor(R.color.primary));
            iv_wallet.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
        } else if (pos == 2) {
            txt_game.setTextColor(getColor(R.color.primary));
            setTextViewDrawableColor(txt_game, R.color.primary);
            iv_game.setColorFilter(getResources().getColor(R.color.primary));
            iv_game.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
        } else if (pos == 3) {
            txt_support.setTextColor(getColor(R.color.primary));
            setTextViewDrawableColor(txt_support, R.color.primary);
            iv_support.setColorFilter(getResources().getColor(R.color.primary));
            iv_support.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
        } else if (pos == 4) {
            txt_profile.setTextColor(getColor(R.color.primary));
            setTextViewDrawableColor(txt_profile, R.color.primary);
            iv_profile.setColorFilter(getResources().getColor(R.color.primary));
            iv_profile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
        }
        view_pager.postDelayed(() -> view_pager.setCurrentItem(pos, true), 100);
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color),
                        PorterDuff.Mode.SRC_IN));
            }
        }
    }

}