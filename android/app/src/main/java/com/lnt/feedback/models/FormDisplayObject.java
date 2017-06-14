package com.lnt.feedback.models;

/**
 * Created by Shreyas on 6/1/2017.
 */

public class FormDisplayObject {

    String formName;
    String formId;

    public FormDisplayObject(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
