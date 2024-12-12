
package com.app.rkvmoneyrecharge.models.state_board_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillDetail {

    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("description")
    @Expose
    private List<Description> description;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Description> getDescription() {
        return description;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }

}
