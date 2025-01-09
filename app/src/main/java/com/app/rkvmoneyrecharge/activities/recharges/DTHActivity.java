package com.app.rkvmoneyrecharge.activities.recharges;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.activities.MainActivity;
import com.app.rkvmoneyrecharge.activities.bbps.PrepaidActivity;
import com.app.rkvmoneyrecharge.adapters.OperatorSpinnerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityDthactivityBinding;
import com.app.rkvmoneyrecharge.databinding.DthInfoDailogBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.all_operator_model.Dynamic;
import com.app.rkvmoneyrecharge.models.all_state_model.AllStateModel;
import com.app.rkvmoneyrecharge.models.dth_info_model.DthInfoModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.recharge_model.RechargeModel;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.RofferPlanModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTHActivity extends BaseActivity {
    LoginModel loginModel;
    ActivityDthactivityBinding binding;
    OperatorSpinnerAdapter operatorSpinnerAdapter;
    Dynamic selectedOperator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dthactivity);
        loginModel = Utility.getLoginUser(getApplicationContext());
        setOperatorAdapter();
        manageClicks();
    }

    private void manageClicks() {

        binding.userInfo.setOnClickListener(v -> {
            if (binding.etMobile.getText().toString().isEmpty()) {
                FToast.makeText(getApplicationContext(), "Please enter valid customer ID", FToast.LENGTH_SHORT).show();
            } else {
                dthCustomerInfo();
            }
        });

        binding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 2) {
                    binding.spiOprator.setSelection(0);
                    selectedOperator = AppData.allOperatorListDTH.get(0);
                } else if (s.length() == 2) {
                    String prefix = s.subSequence(0, 2).toString();
                    for (int i = 0; i < AppData.allOperatorListDTH.size(); i++) {
                        if (AppData.allOperatorListDTH.get(i).getValue().equals(prefix)) {
                            binding.spiOprator.setSelection(i);
                            selectedOperator = AppData.allOperatorListDTH.get(i);
                            break;
                        }
                    }

                } else if (s.length() == 3) {
                    String prefixThree = s.subSequence(0, 3).toString();
                    if (
                            prefixThree.equalsIgnoreCase("015") ||
                                    prefixThree.equalsIgnoreCase("020") ||
                                    prefixThree.equalsIgnoreCase("028") ||
                                    prefixThree.equalsIgnoreCase("029")

                    ) {

                        for (int i = 0; i < AppData.allOperatorListDTH.size(); i++) {
                            if (AppData.allOperatorListDTH.get(i).getText().equalsIgnoreCase("DISHTV")) {
                                binding.spiOprator.setSelection(i);
                                selectedOperator = AppData.allOperatorListDTH.get(i);
                                break;
                            }
                        }

                    } else if (
                            prefixThree.equalsIgnoreCase("300") ||
                                    prefixThree.equalsIgnoreCase("301") ||
                                    prefixThree.equalsIgnoreCase("302") ||
                                    prefixThree.equalsIgnoreCase("303") ||
                                    prefixThree.equalsIgnoreCase("304") ||
                                    prefixThree.equalsIgnoreCase("305") ||
                                    prefixThree.equalsIgnoreCase("306") ||
                                    prefixThree.equalsIgnoreCase("307") ||
                                    prefixThree.equalsIgnoreCase("308") ||
                                    prefixThree.equalsIgnoreCase("309")) {
                        for (int i = 0; i < AppData.allOperatorListDTH.size(); i++) {
                            if (AppData.allOperatorListDTH.get(i).getText().equalsIgnoreCase("AIRTEL DTH")) {
                                binding.spiOprator.setSelection(i);
                                selectedOperator = AppData.allOperatorListDTH.get(i);
                                break;
                            }
                        }

                    }
                } else if (s.length() == 4) {
                    String prefixFour = s.subSequence(0, 4).toString();
                    if (
                            prefixFour.equalsIgnoreCase("3043") ||
                                    prefixFour.equalsIgnoreCase("8295")

                    ) {

                        for (int i = 0; i < AppData.allOperatorListDTH.size(); i++) {
                            if (AppData.allOperatorListDTH.get(i).getText().equalsIgnoreCase("VIDEOCON D2H")) {
                                binding.spiOprator.setSelection(i);
                                selectedOperator = AppData.allOperatorListDTH.get(i);
                                break;
                            }
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.submit.setOnClickListener(v -> {
            if (binding.etMobile.getText().toString().isEmpty()) {
                FToast.makeText(getApplicationContext(), "Please enter valid customer ID", FToast.LENGTH_SHORT).show();
            } else if (binding.etAmount.getText().toString().isEmpty()) {
                FToast.makeText(getApplicationContext(), "Please enter valid amount", FToast.LENGTH_SHORT).show();
            } else {
                rechargeRequestApi();
            }
        });

    }

    private void rechargeRequestApi() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("mobileno", binding.etMobile.getText().toString());
        map.put("amount", binding.etAmount.getText().toString());
        map.put("operator", selectedOperator.getText());
        map.put("state", "");
        map.put("transid", "TXN_" + System.currentTimeMillis());
        map.put("token", AppData.token);
        FLog.w("rechargeRequestApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("rechargeRequestApi>>>>>", "Map>>>>>encMap>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/Recharge", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("rechargeRequestApi>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    RechargeModel rechargeModel = new Gson().fromJson(loginBody, RechargeModel.class);
                    FToast.makeText(getApplicationContext(), rechargeModel.getDynamic().getMessage(), FToast.LENGTH_SHORT).show();
                    if (rechargeModel.getDynamic().getStatus().equalsIgnoreCase("Success")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    private void setOperatorAdapter() {

        if (!AppData.allOperatorListDTH.isEmpty()) {
            selectedOperator = AppData.allOperatorListDTH.get(0);
        }
        operatorSpinnerAdapter = new OperatorSpinnerAdapter(DTHActivity.this, R.layout.state_spinner_adapter_item, AppData.allOperatorListDTH);
        binding.spiOprator.setAdapter(operatorSpinnerAdapter);
        binding.spiOprator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOperator = AppData.allOperatorListDTH.get(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void dthCustomerInfo() {
        String optnm = selectedOperator.getText().toString().replace(" ", "");
        if (optnm.equalsIgnoreCase("VIDEOCOND2H")) {
            optnm = "VIDEOD2H";
        } else if (optnm.equalsIgnoreCase("SUNTV")) {
            optnm = "SunDirect";
        }

        globalLoader.showLoader();
        String url = "https://rkvplan.in/api/dthCustomerInfo?userid=" + AppData.userid + "&token=" + AppData.tokenRKV + "&optnm=" + optnm + "&number=" + binding.etMobile.getText().toString();
        FLog.w("dthCustomerInfo>>>>>", ">URLL>>>>>>>>>>>>>>>>>>>" + url);
        RetrofitClient.getRetrofitInstance().dthCustomerInfo(url).enqueue(new Callback<DthInfoModel>() {
            @Override
            public void onResponse(Call<DthInfoModel> call, Response<DthInfoModel> response) {
                globalLoader.dismissLoader();
                FLog.w("dthCustomerInfo>>>>>", ">>>>>>>>>>>>>>>>>>>>" + new Gson().toJson(response.body()));
                showDthInfoDailog(response.body().getDynamic());

            }

            @Override
            public void onFailure(Call<DthInfoModel> call, Throwable t) {
                globalLoader.dismissLoader();
            }
        });
    }


    void showDthInfoDailog(com.app.rkvmoneyrecharge.models.dth_info_model.Dynamic record) {
        try {


            DthInfoDailogBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                    R.layout.dth_info_dailog, null, false);
            final Dialog dialog = new Dialog(DTHActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogBinding.getRoot());
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialog.show();
//ss
            dialogBinding.etAmount.setText(record.getBalance().equalsIgnoreCase("Not found") ? "" : "" + record.getBalance());
            dialogBinding.setModel(record);
            dialogBinding.cancel.setOnClickListener(v -> {
                binding.etAmount.setText(dialogBinding.etAmount.getText().toString());
                dialog.dismiss();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}