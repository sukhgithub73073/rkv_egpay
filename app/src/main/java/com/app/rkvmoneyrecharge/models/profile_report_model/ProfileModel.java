package com.app.rkvmoneyrecharge.models.profile_report_model;

import java.util.ArrayList;
import java.util.List;

public class ProfileModel {

    public String title ;
    public List<ProfileReportsModel> list = new ArrayList<>() ;

    public ProfileModel(String title, List<ProfileReportsModel> list) {
        this.title = title;
        this.list = list;
    }
}
