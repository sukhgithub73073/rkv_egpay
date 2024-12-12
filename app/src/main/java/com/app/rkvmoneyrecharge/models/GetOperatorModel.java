package com.app.rkvmoneyrecharge.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOperatorModel {

    @SerializedName("postpaid")
    @Expose
    private Boolean postpaid;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("Operator")
    @Expose
    private String operator;
    @SerializedName("Circle")
    @Expose
    private String circle;

    public Boolean getPostpaid() {
        return postpaid;
    }

    public void setPostpaid(Boolean postpaid) {
        this.postpaid = postpaid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

}
