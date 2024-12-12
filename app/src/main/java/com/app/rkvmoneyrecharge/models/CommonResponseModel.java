
package com.app.rkvmoneyrecharge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponseModel {

    @SerializedName("IV")
    @Expose
    private String IV;

    @SerializedName("encrypted")
    @Expose
    private String encrypted;


    public CommonResponseModel(String IV, String encrypted) {
        this.IV = IV;
        this.encrypted = encrypted;
    }

    public String getIV() {
        return IV;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
}
