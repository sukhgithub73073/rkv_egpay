
package com.app.rkvmoneyrecharge.models.stock_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("srno")
    @Expose
    private Integer srno;
    @SerializedName("operatorname")
    @Expose
    private String operatorname;
    @SerializedName("zone")
    @Expose
    private String zone;
    @SerializedName("margin")
    @Expose
    private String margin;
    @SerializedName("avglimit")
    @Expose
    private String avglimit;
    @SerializedName("margincategoryname")
    @Expose
    private String margincategoryname;
    @SerializedName("amount")
    @Expose
    private String amount;

    public Integer getSrno() {
        return srno;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getAvglimit() {
        return avglimit;
    }

    public void setAvglimit(String avglimit) {
        this.avglimit = avglimit;
    }

    public String getMargincategoryname() {
        return margincategoryname;
    }

    public void setMargincategoryname(String margincategoryname) {
        this.margincategoryname = margincategoryname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
