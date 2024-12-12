
package com.app.rkvmoneyrecharge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryModel {


    @SerializedName("dynamic")
    @Expose
    private List<Datum> data = new ArrayList<>() ;

    public List<Datum> getData() {
        return data ==null ? new ArrayList<>() : data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
