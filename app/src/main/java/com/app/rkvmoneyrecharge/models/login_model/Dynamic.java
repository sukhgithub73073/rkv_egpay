
package com.app.rkvmoneyrecharge.models.login_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("logintype")
    @Expose
    private String logintype;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileno")
    @Expose
    private String mobileno = "";
    @SerializedName("token")
    @Expose
    private String token;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
