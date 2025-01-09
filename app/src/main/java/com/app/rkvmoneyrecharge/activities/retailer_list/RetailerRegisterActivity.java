package com.app.rkvmoneyrecharge.activities.retailer_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.activities.LoginActivity;
import com.app.rkvmoneyrecharge.activities.RegisterActivity;
import com.app.rkvmoneyrecharge.adapters.StateSpinnerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityRegisterBinding;
import com.app.rkvmoneyrecharge.databinding.ActivityRetailerRegisterBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.company_info_model.CompanyInfoModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.post_code_model.PostCodeModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.app.rkvmoneyrecharge.utils.GlobalLoader;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerRegisterActivity extends BaseActivity {
    ActivityRetailerRegisterBinding binding;
    String currentState = "";
    String currentCity = "";
    LoginModel loginModel;
    GlobalLoader globalLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retailer_register);
        loginModel = Utility.getLoginUser(getApplicationContext());
        globalLoader = new GlobalLoader(RetailerRegisterActivity.this);
        getCompanyInfo();
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        binding.register.setOnClickListener(v -> {
            registerApiCall();
        });

        binding.etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etPincode.getText().toString().length() == 6) {
                    getPinCodeInfo();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etAadhaar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {

                String input = s.toString().replaceAll("\\s", ""); // Remove existing spaces
                StringBuilder formatted = new StringBuilder();

                // Add space after every 4 characters
                for (int i = 0; i < input.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(" ");
                    }
                    formatted.append(input.charAt(i));
                }

                try {
                    binding.etAadhaar.removeTextChangedListener(this);
                    binding.etAadhaar.setText(formatted.toString());
                    if (binding.etAadhaar.getText().toString().length() != 14) {
                        binding.etAadhaar.setSelection(formatted.length()); // Move cursor to the end
                    }
                    binding.etAadhaar.addTextChangedListener(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCompanyInfo() {

        Map<String, Object> map = new HashMap<>();
        map.put("Name", binding.etName.getText().toString());
        map.put("MobileNo", binding.etMobile.getText().toString());
        FLog.w("getCompanyInfo>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("getCompanyInfo>>>>>", "ENC---Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());


        RetrofitClient.getRetrofitInstance().getCompanyInfo(encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                FLog.w("registerApiCall>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                CompanyInfoModel companyInfoModel = new Gson().fromJson(loginBody, CompanyInfoModel.class);

                StateSpinnerAdapter stateSpinnerAdapter = new StateSpinnerAdapter(RetailerRegisterActivity.this, R.layout.state_spinner_adapter_item, companyInfoModel.getDynamic().getStateList());
                binding.spiState.setAdapter(stateSpinnerAdapter);
                binding.spiState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        currentState = companyInfoModel.getDynamic().getStateList().get(position).getValue();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {

            }
        });


    }

    private void registerApiCall() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("token", AppData.token);

        map.put("name", binding.etName.getText().toString());
        map.put("phoneno", binding.etMobile.getText().toString());
        map.put("aadharno", binding.etAadhaar.getText().toString().replaceAll(" ",""));
        map.put("panno", binding.etPan.getText().toString());
        map.put("emailid", binding.etEmail.getText().toString());
        map.put("address", binding.etAddress.getText().toString());
        map.put("pincode", binding.etPincode.getText().toString());
        map.put("DeviceId", "" + System.currentTimeMillis());
        map.put("city", currentCity);
        map.put("state", currentState);

        FLog.w("registerApiCall>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("registerApiCall>>>>>", "ENC---Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().retailerRegisterRequest(encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("registerApiCall>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    CommonModel userModel = new Gson().fromJson(loginBody, CommonModel.class);
                    FToast.makeText(getApplicationContext(), userModel.getMessage(), FToast.LENGTH_SHORT).show();
                    if (userModel.getStatus()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), RetailerListActivity.class)) ;
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
    private void getPinCodeInfo() {
        String url = "https://api.postalpincode.in/pincode/" + binding.etPincode.getText();
        RetrofitClient.getRetrofitInstance().getPincodeInfo(url).enqueue(new Callback<List<PostCodeModel>>() {
            @Override
            public void onResponse(Call<List<PostCodeModel>> call, Response<List<PostCodeModel>> response) {
                FLog.w("getPinCodeInfo>>>>>", ">>>>>>>>>>>>>>>>>>>>" + response.body().size());
                if (response.body().isEmpty()) {
                } else {
                    //// currentState = response.body().get(0).getPostOffice().get(0).getState();
                    currentCity = response.body().get(0).getPostOffice().get(0).getDistrict();
                }
            }

            @Override
            public void onFailure(Call<List<PostCodeModel>> call, Throwable t) {

            }
        });
    }

}