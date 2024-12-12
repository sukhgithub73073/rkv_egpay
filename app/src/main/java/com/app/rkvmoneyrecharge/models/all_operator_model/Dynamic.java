
package com.app.rkvmoneyrecharge.models.all_operator_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamic {


    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Value")
    @Expose
    private String value;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
