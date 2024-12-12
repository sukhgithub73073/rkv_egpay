
package com.app.rkvmoneyrecharge.models.recharge_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("transid")
    @Expose
    private Object transid;
    @SerializedName("optransid")
    @Expose
    private Object optransid;
    @SerializedName("referenceid")
    @Expose
    private Object referenceid;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("balance")
    @Expose
    private Object balance;
    @SerializedName("margin")
    @Expose
    private String margin;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTransid() {
        return transid;
    }

    public void setTransid(Object transid) {
        this.transid = transid;
    }

    public Object getOptransid() {
        return optransid;
    }

    public void setOptransid(Object optransid) {
        this.optransid = optransid;
    }

    public Object getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(Object referenceid) {
        this.referenceid = referenceid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

}
