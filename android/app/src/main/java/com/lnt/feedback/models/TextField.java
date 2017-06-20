package com.lnt.feedback.models;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class TextField {

    private String title;
    private String description;
    private int inputType = -1;

    public TextField(String title) {
        this.title = title;
    }

    public TextField(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TextField(String title, String description, int inputType) {
        this.title = title;
        this.description = description;
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}
