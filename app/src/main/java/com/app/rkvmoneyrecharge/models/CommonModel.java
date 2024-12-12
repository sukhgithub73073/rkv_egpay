package com.app.rkvmoneyrecharge.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CommonModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dynamic")
    @Expose
    private Object dynamic;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDynamic() {
        return dynamic;
    }

    public void setDynamic(Object dynamic) {
        this.dynamic = dynamic;
    }

}
