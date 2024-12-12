
package com.app.rkvmoneyrecharge.models.roffer_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("offerDetails")
    @Expose
    private String offerDetails;
    @SerializedName("commAmount")
    @Expose
    private String commAmount;
    @SerializedName("commType")
    @Expose
    private String commType;

    public Response(String price, String offer, String offerDetails) {
        this.price = price;
        this.offer = offer;
        this.offerDetails = offerDetails;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

    public String getCommAmount() {
        return commAmount;
    }

    public void setCommAmount(String commAmount) {
        this.commAmount = commAmount;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

}
