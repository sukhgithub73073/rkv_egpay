
package com.app.rkvmoneyrecharge.models.state_board_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("BillDetails")
    @Expose
    private List<BillDetail> billDetails;

    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

}
