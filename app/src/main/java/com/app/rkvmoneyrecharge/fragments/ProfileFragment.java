package com.app.rkvmoneyrecharge.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.activities.HistoryActivity;
import com.app.rkvmoneyrecharge.activities.LedgerActivity;
import com.app.rkvmoneyrecharge.activities.MyTicketsActivity;
import com.app.rkvmoneyrecharge.activities.PolicyActivity;
import com.app.rkvmoneyrecharge.activities.reports.TopupHistoryActivity;
import com.app.rkvmoneyrecharge.activities.retailer_list.RetailerListActivity;
import com.app.rkvmoneyrecharge.activities.retailer_list.RetailerRegisterActivity;
import com.app.rkvmoneyrecharge.adapters.ProfileReportAdapter;
import com.app.rkvmoneyrecharge.databinding.FragmentProfileBinding;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ItemsModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileReportsModel;
import com.app.rkvmoneyrecharge.utils.CommonDB;
import com.app.rkvmoneyrecharge.utils.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    LoginModel loginModel ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile, container, false);
        loginModel = Utility.getLoginUser(getContext()) ;
        binding.txtName.setText("" + loginModel.getDynamic().getName());
        binding.txtMobile.setText("" + loginModel.getDynamic().getUserid());

        setAdapters();
        manageClickes();
        return binding.getRoot();
    }

    private void manageClickes() {

        binding.txtUpdate.setOnClickListener(v -> {
            //   startActivity(new Intent(getContext() , EditProfileActivity.class).putExtra("UPDATE_FLAG" , "UPDATE")) ;
        });
        binding.logout.setOnClickListener(v -> {
            logoutDialog();
        });

    }
    private void logoutDialog() {
        Utility.userLogout(getContext()) ;
//        Utility.commonSweetDialog(SweetAlertDialog.WARNING_TYPE, "Logout",
//                "Are you want to Logout" , getActivity()
//                , new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss() ;
//                        Utility.userLogout(getContext()) ;
//
//                    }
//                });
    }

    private void setAdapters() {
        List<ProfileModel> parentList = new ArrayList<>();

        List<ProfileReportsModel> txn = new ArrayList<>();
        txn.add(new ProfileReportsModel("Api/LedgerReport", "Ledger Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class)));
        txn.add(new ProfileReportsModel("Api/RechargeReport", "Recharge Report", R.drawable.logo, new Intent(getContext(), HistoryActivity.class)));
        txn.add(new ProfileReportsModel("Api/TopUpReport", "Wallet Topup Report", R.drawable.logo, new Intent(getContext(), TopupHistoryActivity.class)));
        txn.add(new ProfileReportsModel("Api/TopUpReport", "Refund Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class)));
        txn.add(new ProfileReportsModel("Api/TopUpReport", "Commission Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class)));
        txn.add(new ProfileReportsModel("Api/TopUpReport", "Refferal Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class)));
        txn.add(new ProfileReportsModel("Api/TopUpReport", "Online Purchase Report", R.drawable.logo, new Intent(getContext(), LedgerActivity.class)));
        parentList.add(new ProfileModel("Transaction Report", txn));

        List<ProfileReportsModel> support = new ArrayList<>();
        support.add(new ProfileReportsModel("", "My Tickets", R.drawable.logo, new Intent(getContext(), MyTicketsActivity.class)));
        support.add(new ProfileReportsModel("", "Change Password", R.drawable.logo, null));

        support.add(new ProfileReportsModel("", "Privacy Policy", R.drawable.logo, new Intent(getContext(), PolicyActivity.class).putExtra("TYPE", "privacy_policy")));
        support.add(new ProfileReportsModel("", "Refund Policy", R.drawable.logo, new Intent(getContext(), PolicyActivity.class).putExtra("TYPE", "refund_condition")));
        support.add(new ProfileReportsModel("", "Terms and Conditions", R.drawable.logo, new Intent(getContext(), PolicyActivity.class).putExtra("TYPE", "terms_conditions")));
        //TODO add later
        // support.add(new ProfileReportsModel("","DTH Tollfree Numbers",R.drawable.logo ,new Intent(getContext() , TollfreeActivity.class))) ;
        parentList.add(new ProfileModel("Support System", support));

        ProfileReportAdapter profileReportAdapter = new ProfileReportAdapter(parentList, getContext(), model -> {

            new CommonDB(getContext()).putString("REPORT_JSON", new Gson().toJson(model));

            if (model.getName().equalsIgnoreCase("Change Password")) {
                changePasswordTypeDialog();
            } else {
                startActivity(model.getIntent());
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        binding.setReportAdapter(profileReportAdapter);
    }

    private void changePasswordTypeDialog() {
//
//        Utility.changePinPass(getActivity() , password->{
//            password.dismiss();
//            startActivity(new Intent(getContext() , ChangePasswordActivity.class));
//        } , pin->{
//            pin.dismiss();
//            startActivity(new Intent(getContext() , ChangePinActivity.class));
//        });


    }

}