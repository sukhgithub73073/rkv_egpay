
package com.app.rkvmoneyrecharge.models.recharge_history_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

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
