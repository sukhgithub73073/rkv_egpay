package com.app.rkvmoneyrecharge.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.PlanAdapter;
import com.app.rkvmoneyrecharge.adapters.RofferPlanAdapter;
import com.app.rkvmoneyrecharge.databinding.FragmentMobilePlanBinding;
import com.app.rkvmoneyrecharge.interfaces.PlanClickedInterface;
import com.app.rkvmoneyrecharge.models.roffer_plan_model.Response;
import com.app.rkvmoneyrecharge.utils.FLog;

import java.util.ArrayList;
import java.util.List;

public class MobilePlanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentMobilePlanBinding binding ;
    RofferPlanAdapter planAdapter ;
    private String mParam1;
    private String mParam2;
    List<Response> allPlanList = new ArrayList<>() ;
    List<Response> planList = new ArrayList<>() ;
    PlanClickedInterface planClickedInterface ;
    public MobilePlanFragment( List<Response> planList , PlanClickedInterface planClickedInterface) {
        this.planList = planList ;
        this.planClickedInterface = planClickedInterface ;
        allPlanList.addAll(planList) ;
    }

    public static MobilePlanFragment newInstance(String param1, String param2) {
        MobilePlanFragment fragment = new MobilePlanFragment(new ArrayList<>()  ,mo->{});
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
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_mobile_plan, container, false);
        FLog.w("fgh", ">>>>>>>>>>" + allPlanList.size());
        planAdapter = new RofferPlanAdapter(planList , pos->{
            planClickedInterface.onPlanClicked(planList.get(pos)) ;
        }) ;
        binding.setPlanAdapter(planAdapter);

//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
        return binding.getRoot();
    }
}