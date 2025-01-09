
package com.app.rkvmoneyrecharge.models.retailer_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("SrNo")
    @Expose
    private Integer srNo;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("BuyerBalance")
    @Expose
    private String buyerBalance;
    @SerializedName("SellerBalance")
    @Expose
    private String sellerBalance;
    @SerializedName("AdminStatus")
    @Expose
    private Boolean adminStatus;
    @SerializedName("BuyerStatus")
    @Expose
    private Boolean buyerStatus;
    @SerializedName("SellerStatus")
    @Expose
    private Boolean sellerStatus;
    @SerializedName("RedeemPurchase")
    @Expose
    private Boolean redeemPurchase;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("PackageName")
    @Expose
    private String packageName;
    @SerializedName("PackageId")
    @Expose
    private String packageId;
    @SerializedName("ReferenceId")
    @Expose
    private Object referenceId;

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBuyerBalance() {
        return buyerBalance;
    }

    public void setBuyerBalance(String buyerBalance) {
        this.buyerBalance = buyerBalance;
    }

    public String getSellerBalance() {
        return sellerBalance;
    }

    public void setSellerBalance(String sellerBalance) {
        this.sellerBalance = sellerBalance;
    }

    public Boolean getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Boolean getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(Boolean buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public Boolean getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(Boolean sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public Boolean getRedeemPurchase() {
        return redeemPurchase;
    }

    public void setRedeemPurchase(Boolean redeemPurchase) {
        this.redeemPurchase = redeemPurchase;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Object getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Object referenceId) {
        this.referenceId = referenceId;
    }

}
