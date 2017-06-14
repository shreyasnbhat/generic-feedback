package com.lnt.feedback.models;

import android.widget.TextView;

import com.hsalf.smilerating.SmileRating;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class RatingField {

    private String description;
    private int rating;

    public RatingField(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
