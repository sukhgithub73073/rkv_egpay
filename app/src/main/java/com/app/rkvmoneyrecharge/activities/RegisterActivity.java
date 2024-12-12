package com.app.rkvmoneyrecharge.activities;

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
import com.app.rkvmoneyrecharge.adapters.RoleSpinnerAdapter;
import com.app.rkvmoneyrecharge.adapters.StateSpinnerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityRegisterBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.company_info_model.CompanyInfoModel;
import com.app.rkvmoneyrecharge.models.company_info_model.Userrole;
import com.app.rkvmoneyrecharge.models.post_code_model.PostCodeModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;
    String currentUserRole = "";
    String currentState = "";
    String currentCity = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        getCompanyInfo();


//        binding.etName.setText("Rajesh");
//        binding.etMobile.setText("8569857485");
//        binding.etAadhaar.setText("6352 5896 6955");
//        binding.etPan.setText("JHGFK9876T");
//        binding.etAddress.setText("mohali");
//        binding.etEmail.setText("mohali@gmail.com");
//        binding.etCompanyName.setText("tets dfgdf pvt ltd");
//        binding.etGst.setText("5454545454632sdfdfd");
//        binding.etWebsite.setText("https://jsonlint.com/");


        binding.txtLogin.setOnClickListener(v -> {
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
                setRoleAdapter(companyInfoModel.getDynamic().getUserrole());

                StateSpinnerAdapter stateSpinnerAdapter = new StateSpinnerAdapter(RegisterActivity.this, R.layout.state_spinner_adapter_item, companyInfoModel.getDynamic().getStateList());
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

    private void setRoleAdapter(List<Userrole> list) {
        RoleSpinnerAdapter stateSpinnerAdapter = new RoleSpinnerAdapter(RegisterActivity.this, R.layout.state_spinner_adapter_item, list);
        binding.spiRole.setAdapter(stateSpinnerAdapter);
        binding.spiRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUserRole = list.get(position).getValue();
            }
            public void onNothingSelected(AdapterView<?> parent) {
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

    private void registerApiCall() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("Name", binding.etName.getText().toString());
        map.put("MobileNo", getIntent().getStringExtra("MobileNo"));
        //map.put("MobileNo", binding.etMobile.getText().toString());
        map.put("AadharNo", binding.etAadhaar.getText().toString());
        map.put("PanNo", binding.etPan.getText().toString());
        map.put("EmailId", binding.etEmail.getText().toString());
        map.put("Address", binding.etAddress.getText().toString());
        map.put("PinCode", binding.etPincode.getText().toString());
        map.put("CompanyName", binding.etCompanyName.getText().toString());
        map.put("GstNo", binding.etGst.getText().toString());
        map.put("Companurl", binding.etWebsite.getText().toString());
        map.put("DeviceId", "" + System.currentTimeMillis());
        map.put("userrole", currentUserRole);
        map.put("City", currentCity);
        map.put("state", currentState);
        map.put("IsMobile", true);
        map.put("IsVerifyMobile", true);

        FLog.w("registerApiCall>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("registerApiCall>>>>>", "ENC---Map>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().registerRequest(encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("registerApiCall>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    CommonModel userModel = new Gson().fromJson(loginBody, CommonModel.class);
                    FToast.makeText(getApplicationContext(), userModel.getMessage(), FToast.LENGTH_SHORT).show();
                    if (userModel.getStatus()) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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