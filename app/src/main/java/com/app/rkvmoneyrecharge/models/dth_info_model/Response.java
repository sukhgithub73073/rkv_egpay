
package com.app.rkvmoneyrecharge.models.dth_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("currentbalance")
    @Expose
    private String currentbalance;
    @SerializedName("validTill")
    @Expose
    private String validTill;
    @SerializedName("SubscriberName")
    @Expose
    private Object subscriberName;
    @SerializedName("SubsBalance")
    @Expose
    private Object subsBalance;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("State")
    @Expose
    private Object state;
    @SerializedName("ActivatedOn")
    @Expose
    private Object activatedOn;
    @SerializedName("SchemeName")
    @Expose
    private Object schemeName;
    @SerializedName("StatusName")
    @Expose
    private Object statusName;
    @SerializedName("PaytermName")
    @Expose
    private Object paytermName;
    @SerializedName("PackagePrice")
    @Expose
    private Object packagePrice;
    @SerializedName("rechargedueDate")
    @Expose
    private Object rechargedueDate;
    @SerializedName("monthlyRecharge")
    @Expose
    private Object monthlyRecharge;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("customerName")
    @Expose
    private Object customerName;
    @SerializedName("customerStatus")
    @Expose
    private Object customerStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(String currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public Object getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(Object subscriberName) {
        this.subscriberName = subscriberName;
    }

    public Object getSubsBalance() {
        return subsBalance;
    }

    public void setSubsBalance(Object subsBalance) {
        this.subsBalance = subsBalance;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Object activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Object getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(Object schemeName) {
        this.schemeName = schemeName;
    }

    public Object getStatusName() {
        return statusName;
    }

    public void setStatusName(Object statusName) {
        this.statusName = statusName;
    }

    public Object getPaytermName() {
        return paytermName;
    }

    public void setPaytermName(Object paytermName) {
        this.paytermName = paytermName;
    }

    public Object getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Object packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Object getRechargedueDate() {
        return rechargedueDate;
    }

    public void setRechargedueDate(Object rechargedueDate) {
        this.rechargedueDate = rechargedueDate;
    }

    public Object getMonthlyRecharge() {
        return monthlyRecharge;
    }

    public void setMonthlyRecharge(Object monthlyRecharge) {
        this.monthlyRecharge = monthlyRecharge;
    }

    public String getBalance() {
        return balance ;

    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Object getCustomerName() {
        return customerName;
    }

    public void setCustomerName(Object customerName) {
        this.customerName = customerName;
    }

    public Object getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(Object customerStatus) {
        this.customerStatus = customerStatus;
    }

}
