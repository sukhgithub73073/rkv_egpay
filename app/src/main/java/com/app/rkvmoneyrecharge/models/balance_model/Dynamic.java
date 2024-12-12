
package com.app.rkvmoneyrecharge.models.balance_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("BuyerBalance")
    @Expose
    private String buyerBalance;

    public String getBuyerBalance() {
        return buyerBalance;
    }

    public void setBuyerBalance(String buyerBalance) {
        this.buyerBalance = buyerBalance;
    }

}
