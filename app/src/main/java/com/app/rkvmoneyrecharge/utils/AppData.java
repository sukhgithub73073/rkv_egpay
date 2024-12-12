package com.app.rkvmoneyrecharge.utils;

import com.app.rkvmoneyrecharge.models.all_operator_model.Dynamic;
import com.app.rkvmoneyrecharge.models.check_plan_model.CheckPlanModel;
import com.app.rkvmoneyrecharge.models.profile_report_model.ProfileReportsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    public  static String tokenRKV = "F6E406C3-7BF1-4095-9422-4FC17257CFC2";
    public  static String userid = "1234567";

    public  static String selectedAmount = "" ;
    public  static ProfileReportsModel profileReportsModel ;
    public  static CheckPlanModel checkPlanModel ;
    public  static String commingSoonTitle = "" ;
    public  static String token = "" ;
    public  static List<Dynamic> allOperatorList = new ArrayList<>() ;
    public  static List<Dynamic> allOperatorListDTH = new ArrayList<>() ;
    public  static List<com.app.rkvmoneyrecharge.models.all_state_model.Dynamic> allStateList = new ArrayList<>() ;
    public static Map<String, Object> map = new HashMap<>();
}
