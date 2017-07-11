package com.lnt.feedback.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.lnt.feedback.R;
import com.lnt.feedback.models.RatingField;
import com.lnt.feedback.util.DataSaveUtil;

import static android.os.Build.VERSION_CODES.BASE;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class RatingTypeViewHolder extends RecyclerView.ViewHolder {

    private RatingField ratingField;

    private TextView description;
    private SmileRating smileRating;

    public RatingTypeViewHolder(View itemView) {
        super(itemView);
        description = (TextView) itemView.findViewById(R.id.description);
        smileRating = (SmileRating) itemView.findViewById(R.id.smile_rating);

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                if(!reselected) {
                    DataSaveUtil.dataMap.put(description.getText().toString(), level + "");
                }
                else{
                    DataSaveUtil.dataMap.put(description.getText().toString(), "3");
                }
            }
        });
    }

    public void setRatingField(RatingField ratingField) {
        this.ratingField = ratingField;
    }

    public void bind() {

        description.setText(ratingField.getDescription());

        if(DataSaveUtil.dataMap.get(description.getText().toString()) != null){
            switch(Integer.valueOf(DataSaveUtil.dataMap.get(description.getText().toString()))){

                case SmileRating.GREAT :
                    smileRating.setSelectedSmile(SmileRating.GREAT,false);
                    break;
                case SmileRating.GOOD :
                    smileRating.setSelectedSmile(SmileRating.GOOD,false);
                    break;
                case SmileRating.OKAY :
                    smileRating.setSelectedSmile(SmileRating.OKAY,false);
                    break;
                case SmileRating.BAD:
                    smileRating.setSelectedSmile(SmileRating.BAD,false);
                    break;
                case SmileRating.TERRIBLE :
                    smileRating.setSelectedSmile(SmileRating.TERRIBLE,false);
                    break;
                default:
                    break;
            }
        }



    }
}
