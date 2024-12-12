
package com.app.rkvmoneyrecharge.models.dth_info_model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Response")
    @Expose
    private Response response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}