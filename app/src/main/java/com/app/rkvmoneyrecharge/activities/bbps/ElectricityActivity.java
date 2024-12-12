package com.app.rkvmoneyrecharge.activities.bbps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.CommonSpinnerAdapter;
import com.app.rkvmoneyrecharge.adapters.RechargeStateSpinnerAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityElectricityBinding;
import com.app.rkvmoneyrecharge.models.CommonSpinnerModel;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.RofferPlanModel;
import com.app.rkvmoneyrecharge.models.state_board_model.StateBoardModel;
import com.app.rkvmoneyrecharge.models.state_model.StateModel;
import com.app.rkvmoneyrecharge.retrofit.RetrofitClient;
import com.app.rkvmoneyrecharge.utils.AppData;
import com.app.rkvmoneyrecharge.utils.FLog;
import com.app.rkvmoneyrecharge.utils.FToast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricityActivity extends BaseActivity {
    ActivityElectricityBinding binding;
    List<CommonSpinnerModel> stateList = new ArrayList<>();
    List<CommonSpinnerModel> boardList = new ArrayList<>();
    CommonSpinnerModel selectedState, selectedBoard;
    StateBoardModel stateBoardModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_electricity);
        boardDetails();
        manageClickListener();

    }

    private void manageClickListener() {
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.submit.setOnClickListener(v -> {
            viewElectricityBill();
        });
    }

    private void viewElectricityBill() {
        String url = "https://rkvplan.in/api/viewElectricityBill?userid=" + AppData.userid + "&token=" + AppData.tokenRKV
                + "&StateName=" + selectedState.getValue() + "&BoardName=" + selectedBoard.getValue()
                + "&account=" + binding.etAccount.getText().toString() + "&ConsumerNumber=" + binding.etConsumerNo.getText().toString();
        FLog.w("viewElectricityBill>>>>>", ">URLL>>>>>>>>>>>>>>>>>>>" + url);

    }

    private void boardDetails() {
        String url = "https://rkvplan.in/api/boardDetails";
        FLog.w("boardDetails>>>>>", ">URLL>>>>>>>>>>>>>>>>>>>" + url);

        RetrofitClient.getRetrofitInstance().boardDetails(url).enqueue(new Callback<StateBoardModel>() {
            @Override
            public void onResponse(Call<StateBoardModel> call, Response<StateBoardModel> response) {
                FLog.w("boardDetails>>>>>", ">>>>>>>>>>>>>>>>>>>>" + new Gson().toJson(response.body()));
                stateBoardModel = response.body();

                stateList.clear();
                stateBoardModel.getDynamic().getBillDetails().forEach(billDetail ->
                        stateList.add(new CommonSpinnerModel(billDetail.getState(), billDetail.getState()))
                );

                boardList.clear();
                if (!stateBoardModel.getDynamic().getBillDetails().isEmpty()) {
                    stateBoardModel.getDynamic().getBillDetails().get(0).getDescription().forEach(description ->
                            boardList.add(new CommonSpinnerModel(description.getBoardname(), description.getBoardname()))
                    );

                }

                setStateAdapter();
                setBoardAdapter();
            }

            @Override
            public void onFailure(Call<StateBoardModel> call, Throwable t) {
            }
        });
    }

    void setStateAdapter() {
        if (!stateList.isEmpty()) {
            selectedState = stateList.get(0);
        }

        CommonSpinnerAdapter rechargeStateSpinnerAdapter = new CommonSpinnerAdapter(ElectricityActivity.this, R.layout.state_spinner_adapter_item, stateList);
        binding.spiState.setAdapter(rechargeStateSpinnerAdapter);
        binding.spiState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boardList.clear();
                selectedState = stateList.get(position);
                stateBoardModel.getDynamic().getBillDetails().stream()
                        .filter(billDetail -> billDetail.getState().equalsIgnoreCase(selectedState.getValue()))
                        .forEach(billDetail ->
                                billDetail.getDescription().forEach(description ->
                                        boardList.add(new CommonSpinnerModel(description.getBoardname(), description.getBoardname()))
                                )
                        );
                setBoardAdapter();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    void setBoardAdapter() {
        if (!boardList.isEmpty()) {
            selectedBoard = boardList.get(0);
        }

        CommonSpinnerAdapter rechargeStateSpinnerAdapter = new CommonSpinnerAdapter(ElectricityActivity.this, R.layout.state_spinner_adapter_item, boardList);
        binding.spiOprator.setAdapter(rechargeStateSpinnerAdapter);
        binding.spiOprator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBoard = boardList.get(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}