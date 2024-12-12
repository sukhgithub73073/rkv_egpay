
package com.app.rkvmoneyrecharge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("rechargeid")
    @Expose
    private String rechargeid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("apitransid")
    @Expose
    private String apitransid;
    @SerializedName("userbusinessname")
    @Expose
    private String userbusinessname;
    @SerializedName("operatorname")
    @Expose
    private String operatorname;
    @SerializedName("margincategoryname")
    @Expose
    private String margincategoryname;
    @SerializedName("statename")
    @Expose
    private String statename;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("operatorId")
    @Expose
    private String operatorId="";
    @SerializedName("mobile")
    @Expose
    private String mobile="";
    @SerializedName("updateTime")
    @Expose
    private String updateTime="";
    @SerializedName("commission")
    @Expose
    private String commission="";
    @SerializedName("chargeAmount")
    @Expose
    private String chargeAmount="";


    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getRechargeid() {
        return rechargeid;
    }

    public void setRechargeid(String rechargeid) {
        this.rechargeid = rechargeid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApitransid() {
        return apitransid;
    }

    public void setApitransid(String apitransid) {
        this.apitransid = apitransid;
    }

    public String getUserbusinessname() {
        return userbusinessname;
    }

    public void setUserbusinessname(String userbusinessname) {
        this.userbusinessname = userbusinessname;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getMargincategoryname() {
        return margincategoryname;
    }

    public void setMargincategoryname(String margincategoryname) {
        this.margincategoryname = margincategoryname;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
