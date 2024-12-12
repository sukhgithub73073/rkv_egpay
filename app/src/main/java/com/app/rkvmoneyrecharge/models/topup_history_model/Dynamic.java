
package com.app.rkvmoneyrecharge.models.topup_history_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("openingbalance")
    @Expose
    private String openingbalance;
    @SerializedName("closingbalance")
    @Expose
    private String closingbalance;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("remark1")
    @Expose
    private String remark1;
    @SerializedName("remark2")
    @Expose
    private String remark2;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpeningbalance() {
        return openingbalance;
    }

    public void setOpeningbalance(String openingbalance) {
        this.openingbalance = openingbalance;
    }

    public String getClosingbalance() {
        return closingbalance;
    }

    public void setClosingbalance(String closingbalance) {
        this.closingbalance = closingbalance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

}
