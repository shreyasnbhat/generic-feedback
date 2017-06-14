package com.lnt.feedback.models;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class TextField {

    private String title;
    private String hint;
    private int inputType = -1;

    public TextField(String title) {
        this.title = title;
    }

    public TextField(String title, String hint) {
        this.title = title;
        this.hint = hint;
    }

    public TextField(String title, String hint, int inputType) {
        this.title = title;
        this.hint = hint;
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}
