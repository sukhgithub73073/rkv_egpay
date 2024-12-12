
package com.app.rkvmoneyrecharge.models.company_info_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("CompanyName")
    @Expose
    private Object companyName;
    @SerializedName("Name")
    @Expose
    private Object name;
    @SerializedName("EmailId")
    @Expose
    private Object emailId;
    @SerializedName("MobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("IsMobile")
    @Expose
    private Boolean isMobile;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("City")
    @Expose
    private Object city;
    @SerializedName("StateList")
    @Expose
    private List<StateModel> stateList;
    @SerializedName("PinCode")
    @Expose
    private Object pinCode;
    @SerializedName("PanNo")
    @Expose
    private Object panNo;
    @SerializedName("AadharNo")
    @Expose
    private Object aadharNo;
    @SerializedName("GstNo")
    @Expose
    private Object gstNo;
    @SerializedName("Companurl")
    @Expose
    private Object companurl;
    @SerializedName("userrole")
    @Expose
    private List<Userrole> userrole;

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getEmailId() {
        return emailId;
    }

    public void setEmailId(Object emailId) {
        this.emailId = emailId;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public List<StateModel> getStateList() {
        return stateList;
    }

    public void setStateList(List<StateModel> stateList) {
        this.stateList = stateList;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
    }

    public Object getPanNo() {
        return panNo;
    }

    public void setPanNo(Object panNo) {
        this.panNo = panNo;
    }

    public Object getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(Object aadharNo) {
        this.aadharNo = aadharNo;
    }

    public Object getGstNo() {
        return gstNo;
    }

    public void setGstNo(Object gstNo) {
        this.gstNo = gstNo;
    }

    public Object getCompanurl() {
        return companurl;
    }

    public void setCompanurl(Object companurl) {
        this.companurl = companurl;
    }

    public List<Userrole> getUserrole() {
        return userrole;
    }

    public void setUserrole(List<Userrole> userrole) {
        this.userrole = userrole;
    }

}
