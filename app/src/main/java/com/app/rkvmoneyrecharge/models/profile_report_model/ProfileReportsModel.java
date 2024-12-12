package com.app.rkvmoneyrecharge.models.profile_report_model;

import android.content.Intent;

public class ProfileReportsModel {
    String name , url ;
    int icon ;
    Intent intent ;

    public ProfileReportsModel(String url,String name, int icon, Intent intent) {
        this.url = url;
        this.name = name;
        this.icon = icon;
        this.intent = intent;
    }





    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
