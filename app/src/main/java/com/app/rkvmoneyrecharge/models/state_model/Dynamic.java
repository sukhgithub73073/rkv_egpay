
package com.app.rkvmoneyrecharge.models.state_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {

    @SerializedName("StateName")
    @Expose
    private List<String> stateName;

    public List<String> getStateName() {
        return stateName;
    }

    public void setStateName(List<String> stateName) {
        this.stateName = stateName;
    }

}
