package com.lnt.feedback.models;

import java.util.ArrayList;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class SpinnerChoice {

    private ArrayList<String> options;
    private String description;

    public SpinnerChoice(String description, ArrayList<String> options) {
        this.description = description;
        this.options = options;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
