
package com.app.rkvmoneyrecharge.models.dth_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("monthlyrecharge")
    @Expose
    private String monthlyrecharge;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nextrechargedate")
    @Expose
    private String nextrechargedate;
    @SerializedName("lastrechargeamount")
    @Expose
    private String lastrechargeamount;
    @SerializedName("lastrechargedate")
    @Expose
    private String lastrechargedate;
    @SerializedName("planname")
    @Expose
    private String planname;
    @SerializedName("operator")
    @Expose
    private String operator;

    public String getMonthlyrecharge() {
        return monthlyrecharge;
    }

    public void setMonthlyrecharge(String monthlyrecharge) {
        this.monthlyrecharge = monthlyrecharge;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextrechargedate() {
        return nextrechargedate;
    }

    public void setNextrechargedate(String nextrechargedate) {
        this.nextrechargedate = nextrechargedate;
    }

    public String getLastrechargeamount() {
        return lastrechargeamount;
    }

    public void setLastrechargeamount(String lastrechargeamount) {
        this.lastrechargeamount = lastrechargeamount;
    }

    public String getLastrechargedate() {
        return lastrechargedate;
    }

    public void setLastrechargedate(String lastrechargedate) {
        this.lastrechargedate = lastrechargedate;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
