package com.app.rkvmoneyrecharge.activities.bbps;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.activities.MainActivity;
import com.app.rkvmoneyrecharge.activities.RegisterActivity;
import com.app.rkvmoneyrecharge.activities.recharges.PhoneNumberActivity;
import com.app.rkvmoneyrecharge.activities.recharges.PlanActivity;
import com.app.rkvmoneyrecharge.activities.recharges.RofferActivity;
import com.app.rkvmoneyrecharge.adapters.OperatorSpinnerAdapter;
import com.app.rkvmoneyrecharge.adapters.PlanAdapter;
import com.app.rkvmoneyrecharge.adapters.RechargeStateSpinnerAdapter;
import com.app.rkvmoneyrecharge.adapters.StateSpinnerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityPrepaidBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.interfaces.CommonInterface;
import com.app.rkvmoneyrecharge.models.CommonModel;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.GetOperatorModel;
import com.app.rkvmoneyrecharge.models.TransactionHistoryModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.recharge_model.RechargeModel;
import com.app.rkvmoneyrecharge.models.roffer_model.RofferModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.app.rkvmoneyrecharge.utils.GlobalLoader;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrepaidActivity extends BaseActivity {
    ActivityPrepaidBinding binding;
    String Operator = "", state = "";
    GlobalLoader globalLoader;
    LoginModel loginModel;
    PlanAdapter planAdapter;
    List<com.app.rkvmoneyrecharge.models.roffer_model.Response> planList = new ArrayList<>();
    RechargeStateSpinnerAdapter rechargeStateSpinnerAdapter;
    OperatorSpinnerAdapter operatorSpinnerAdapter;
    String operatorid = "";
    int PERMISSIONS_REQUEST_READ_CONTACTS = 900;
    int REQUEST_CONTACT = 901;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginModel = Utility.getLoginUser(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prepaid);
        binding.checkBoxTupop.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.checkBoxStv.setOnCheckedChangeListener(onCheckedChangeListener);

        setSpinnerAdapters();
        globalLoader = new GlobalLoader(PrepaidActivity.this);


        manageClickListeners();
        // setupAnimation() ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AppData.selectedAmount.equalsIgnoreCase("")) {
            binding.etAmount.setText(AppData.selectedAmount);
            AppData.selectedAmount = "";
        }
    }

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == binding.checkBoxTupop) {
                    for (int i = 0; i < AppData.allOperatorList.size(); i++) {
                        if (AppData.allOperatorList.get(i).getText().toLowerCase().equalsIgnoreCase(binding.checkBoxTupop.getText().toString().toLowerCase())) {
                            operatorid = AppData.allOperatorList.get(i).getValue();
                            Operator = AppData.allOperatorList.get(i).getText();
                        }

                    }
                    binding.checkBoxTupop.setChecked(true);
                    binding.checkBoxStv.setChecked(false);
                } else if (buttonView == binding.checkBoxStv) {
                    for (int i = 0; i < AppData.allOperatorList.size(); i++) {
                        if (AppData.allOperatorList.get(i).getText().toLowerCase().equalsIgnoreCase(binding.checkBoxStv.getText().toString().toLowerCase())) {
                            operatorid = AppData.allOperatorList.get(i).getValue();
                            Operator = AppData.allOperatorList.get(i).getText();
                        }
                    }
                    binding.checkBoxTupop.setChecked(false);
                    binding.checkBoxStv.setChecked(true);
                }
            }
        }
    };

    private void setSpinnerAdapters() {
        operatorSpinnerAdapter = new OperatorSpinnerAdapter(PrepaidActivity.this, R.layout.state_spinner_adapter_item, AppData.allOperatorList);
        binding.spiOprator.setAdapter(operatorSpinnerAdapter);
        binding.spiOprator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (AppData.allOperatorList.get(position).getText().toLowerCase().contains("bsnl")) {
                    binding.linBsnl.setVisibility(View.VISIBLE);
                } else {
                    binding.linBsnl.setVisibility(View.GONE);
                }
                Operator = AppData.allOperatorList.get(position).getText();

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        rechargeStateSpinnerAdapter = new RechargeStateSpinnerAdapter(PrepaidActivity.this, R.layout.state_spinner_adapter_item, AppData.allStateList);
        binding.spiState.setAdapter(rechargeStateSpinnerAdapter);
        binding.spiState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void manageClickListeners() {
        binding.ivPhoneBook.setOnClickListener(v -> {
            checkContactsPermission();
        });

        binding.txtPlan.setOnClickListener(v -> {
            if (binding.etMobile.getText().toString().length() != 10) {
                FToast.makeText(getApplicationContext(), "Please enter valid 10 digit mobile number", FToast.LENGTH_SHORT).show();
            } else {
                AppData.map.clear();
                AppData.map.put("userid", loginModel.getDynamic().getUserid());
                AppData.map.put("mobileno", binding.etMobile.getText().toString());
                AppData.map.put("operatorid", operatorid);
                AppData.map.put("token", AppData.token);

                startActivity(new Intent(getApplicationContext(), PlanActivity.class));
            }
        });

        binding.browsPlan.setOnClickListener(v -> {
            if (binding.etMobile.getText().toString().length() != 10) {
                FToast.makeText(getApplicationContext(), "Please enter valid 10 digit mobile number", FToast.LENGTH_SHORT).show();
            } else {
                AppData.map.clear();
                AppData.map.put("userid", loginModel.getDynamic().getUserid());
                AppData.map.put("mobileno", binding.etMobile.getText().toString());
                AppData.map.put("operatorid", operatorid);
                AppData.map.put("token", AppData.token);
                AppData.map.put("Operator", Operator);
                AppData.map.put("state", state);

                startActivity(new Intent(getApplicationContext(), RofferActivity.class));
            }
        });
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.submit.setOnClickListener(v -> {
            // if(true){showLottieDialog();}else
            if (binding.etMobile.getText().toString().length() != 10) {
                FToast.makeText(getApplicationContext(), "Please enter valid 10 digit mobile number", FToast.LENGTH_SHORT).show();
            } else if (binding.etAmount.getText().toString().isEmpty()) {
                FToast.makeText(getApplicationContext(), "Please enter valid amount", FToast.LENGTH_SHORT).show();
            } else {
                rechargeRequestApi();
            }
        });


        binding.etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etMobile.getText().toString().length() == 10) {
                    getOperatorApi();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void rechargeRequestApi() {
        globalLoader.showLoader();

        Map<String, Object> map = new HashMap<>();
        map.put("userid", loginModel.getDynamic().getUserid());
        map.put("mobileno", binding.etMobile.getText().toString());
        map.put("amount", binding.etAmount.getText().toString());
        map.put("operator", Operator);
        //  map.put("operator", "10");
        map.put("state", state);
        map.put("transid", "TXN_" + System.currentTimeMillis());
        map.put("token", AppData.token);
        FLog.w("rechargeRequestApi>>>>>", "Map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        Map<String, String> encMap = EncDecRepository.getEncryption(map);
        FLog.w("rechargeRequestApi>>>>>", "encMap>>>>>>>>>>>>>>>>>>>>" + encMap.toString());

        RetrofitClient.getRetrofitInstance().commonPostRequest("Api/Recharge", encMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    globalLoader.dismissLoader();
                    String loginBody = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("rechargeRequestApi>>>>>", ">>>>>>>>>>>>>>>>>>>>" + loginBody);
                    RechargeModel rechargeModel = new Gson().fromJson(loginBody, RechargeModel.class);
//                    FToast.makeText(getApplicationContext(), rechargeModel.getDynamic().getMessage(), FToast.LENGTH_LONG).show();
                    if (rechargeModel.getDynamic().getStatus().equalsIgnoreCase("Success")) {
                        showLottieDialog();
                    } else {
                        showErrorDialog();
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

    private void getOperatorApi() {
        Operator = "";
        state = "";
        operatorid = "";
        globalLoader.showLoader();
        String url = "https://digitalapiproxy.paytm.com/v1/mobile/getopcirclebyrange?channel=web&version=3&number=" + binding.etMobile.getText() + "&child_site_id=1&site_id=1&locale=en-in";
        RetrofitClient.getRetrofitInstance().getOperatorFromMobile(url).enqueue(new Callback<GetOperatorModel>() {
            @Override
            public void onResponse(Call<GetOperatorModel> call, Response<GetOperatorModel> response) {
                try {
                    globalLoader.dismissLoader();
                    FLog.w("getOperatorApi", ">>>>>>>>>>>>>>>" + new Gson().toJson(response.body()));
                    if (response.body().getOperator() != null) {
                        Operator = response.body().getOperator();
                        String op = Operator;
                        if (op.equalsIgnoreCase("Vodafone Idea")) {
                            op = "Vodafone";
                        }
                        int index = -1;
                        for (int i = 0; i < AppData.allOperatorList.size(); i++) {
                            if (AppData.allOperatorList.get(i).getText().toLowerCase().contains(op.toLowerCase())) {
                                index = i;
                                operatorid = AppData.allOperatorList.get(i).getValue();
                                Operator = AppData.allOperatorList.get(i).getText();
                                break;
                            }
                        }
                        if (index != -1) {
                            binding.spiOprator.setSelection(index);
                        }
                    }
                    if (response.body().getCircle() != null) {
                        state = response.body().getCircle();

                        String st = state;
                        if (state.equalsIgnoreCase("All Circles")) {
                            st = "Rajasthan";
                        }
                        int index = -1;  // Default to -1 if not found
                        for (int i = 0; i < AppData.allStateList.size(); i++) {
                            if (AppData.allStateList.get(i).getText().toLowerCase().trim().contains(st.toLowerCase().trim())) {
                                index = i;
                                break;
                            }
                        }

                        if (index != -1) {
                            binding.spiState.setSelection(index);
                        }

                    }
                    // getROfferApi();
                } catch (Exception e) {
                    FLog.w("getOperatorApi", "Exception" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetOperatorModel> call, Throwable t) {
                globalLoader.dismissLoader();
                FLog.w("getOperatorApi", "onFailure" + t.getMessage());
            }
        });
    }


    private void checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted; start PhoneNumberActivity
            openPhoneNumberActivity();
        } else {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    private void openPhoneNumberActivity() {
        Intent intent = new Intent(PrepaidActivity.this, PhoneNumberActivity.class);
        startActivityForResult(intent, REQUEST_CONTACT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted; open PhoneNumberActivity
                openPhoneNumberActivity();
            } else {
                // Permission was denied; show a message to the user
                Toast.makeText(this, "Permission to access contacts is required to select a contact.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONTACT && resultCode == RESULT_OK && data != null) {
            String selectedPhoneNumber = data.getStringExtra("selectedPhoneNumber");
            binding.etMobile.setText(selectedPhoneNumber);
        }
    }

    private void showLottieDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.success_dialog);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(false);
        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.lottieAnimationView);
        LottieAnimationView lottieAnimationView1 = dialog.findViewById(R.id.lottieAnimationView1);
        lottieAnimationView.playAnimation();
        lottieAnimationView1.playAnimation();
        dialog.show();
        dialog.setOnDismissListener(s -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        dialog.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            dialog.dismiss();

        });
    }

    private void showErrorDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_dialog);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(false);
        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();
        dialog.show();
        dialog.setOnDismissListener(s -> {
            //dialog.dismiss();
        });

        dialog.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            dialog.dismiss();

        });
    }

}