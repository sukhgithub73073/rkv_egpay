
package com.app.rkvmoneyrecharge.models.dth_info_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DthInfoModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dynamic")
    @Expose
    private Dynamic dynamic;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

}
