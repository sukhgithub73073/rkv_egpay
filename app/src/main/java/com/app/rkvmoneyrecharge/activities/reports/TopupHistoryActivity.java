package com.app.rkvmoneyrecharge.activities.reports;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.HistoryTxnAdapter;
import com.app.rkvmoneyrecharge.adapters.TopupHistoryAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityHistoryBinding;
import com.app.rkvmoneyrecharge.databinding.ActivityTopupHistoryBinding;
import com.app.rkvmoneyrecharge.enc.EncDecRepository;
import com.app.rkvmoneyrecharge.models.CommonResponseModel;
import com.app.rkvmoneyrecharge.models.Datum;
import com.app.rkvmoneyrecharge.models.TransactionHistoryModel;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ItemsModel;
import com.app.rkvmoneyrecharge.models.topup_history_model.Dynamic;
import com.app.rkvmoneyrecharge.models.topup_history_model.TopupHistoryModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.CommonDB;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupHistoryActivity extends BaseActivity {
    ActivityTopupHistoryBinding binding;

    List<String> monthList = new ArrayList<>();
    String startDate = "", endDate = "";
    boolean load_more = false;
    int page = 0;
    List<Dynamic> allList = new ArrayList<>();
    List<Dynamic> list = new ArrayList<>();
    TopupHistoryAdapter transactionsAdapter;
    ItemsModel model;
    String STATUS = "ALL";
    LoginModel loginModel ;
    TextView date_from, date_to;

    Calendar startCal = Calendar.getInstance(),
            endCal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topup_history);
        loginModel = Utility.getLoginUser(getApplicationContext()) ;

        monthList = Utility.getMonthList();
        try {

            model = new Gson().fromJson(new CommonDB(getApplicationContext()).getString("REPORT_JSON"), ItemsModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews();
        binding.filter.setOnClickListener(v -> {
        });
        getTransactionHistory("oncreate");
        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
//        binding.txtDate.setOnClickListener(v->{
//            datePickerWithType("date_from") ;
//        });
//        binding.dateTo.setOnClickListener(v->{
//            datePickerWithType("date_to") ;
//        });
        binding.search.setOnClickListener(v -> {
            page = 0;
            getTransactionHistory("search");
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();
                if (binding.etSearch.getText().toString().equalsIgnoreCase("")) {
                    list.addAll(allList);
                } else {
                    for (Dynamic datum : allList) {
                        if (datum.getName().toString().toLowerCase().contains(binding.etSearch.getText().toString().toLowerCase())) {
                            list.add(datum);
                        }
                    }
                }
                binding.noResult.setVisibility(list.size() > 0 ? View.GONE : View.VISIBLE);
                binding.getReportAdapter().notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        int m = mMonth + 1;
        startDate = mDay + "/" + m + "/" + mYear;
        endDate = mDay + "/" + m + "/" + mYear;
        startDate = format.format(c.getTime());
        endDate = format.format(c.getTime());
        binding.txtDate.setText(mDay + "-" + monthList.get(mMonth) + "-" + mYear);
        binding.dateTo.setText(mDay + "-" + monthList.get(mMonth) + "-" + mYear);
        transactionsAdapter = new TopupHistoryAdapter(model.getUrl(), getApplicationContext(), list, (p) -> {

        });
        binding.setReportAdapter(transactionsAdapter);
    }

    public void getTransactionHistory(String type) {
        load_more = false;
        if (!type.equalsIgnoreCase("oncreate")) {
            globalLoader.showLoader();
        }
        String url = model.getUrl();
        Map<String, Object> map = new HashMap<>();
        Map<String, String> reqMap = new HashMap<>();

        map.put("userid", "" + loginModel.getDynamic().getUserid());
        map.put("mobileno", "" );
        map.put("amount", "" );
        map.put("operatorid", "" );
        map.put("stateid", "" );
        map.put("rechargestatus", "" );
        map.put("date", Utility.getTodayDate());
        map.put("token", AppData.token);
        FLog.w("getTransactionHistory>>>>>", "map>>>>>>>>>>>>>>>>>>>>" + map.toString());

        try {
            reqMap = EncDecRepository.getEncryption(map);
            FLog.w("getTransactionHistory>>>>>", "reqMap>>>>>>>>>>>>>>>>>>>>" + reqMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RetrofitClient.getRetrofitInstance().commonPostRequest(url, reqMap).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    FLog.w("getTransactionHistory>>>", "onResponse:" + new Gson().toJson(response.body()));
                    if (!type.equalsIgnoreCase("oncreate")) {
                        globalLoader.dismissLoader();
                    }
                    if (page == 0) {
                        allList.clear();
                        list.clear();
                    }
                    String body = EncDecRepository.getDecryption(response.body().getEncrypted(), response.body().getIV());
                    FLog.w("getTransactionHistory>>>>>", ">>>>>>>>>>>>>>>>>>>>" + body);

                    TopupHistoryModel historyModel = new Gson().fromJson(body, TopupHistoryModel.class);
                    if (historyModel.getDynamic().size() > 0) {
                        page++;
                        allList.addAll(historyModel.getDynamic());
                        list.addAll(historyModel.getDynamic());
                        load_more = true;
                    } else {
                        load_more = false;
                    }
                    transactionsAdapter.notifyDataSetChanged();
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


}