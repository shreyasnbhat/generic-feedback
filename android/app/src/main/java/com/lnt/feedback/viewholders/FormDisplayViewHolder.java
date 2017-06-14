package com.lnt.feedback.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lnt.feedback.R;
import com.lnt.feedback.activities.FormActivity;
import com.lnt.feedback.models.FormDisplayObject;

/**
 * Created by Shreyas on 6/1/2017.
 */

public class FormDisplayViewHolder extends RecyclerView.ViewHolder  {

    private FormDisplayObject formDisplayObject;
    private CardView cardFrame;
    private TextView formTitle;
    private TextView circleIndicator;

    public FormDisplayViewHolder(View itemView, final Context context) {
        super(itemView);
        formTitle = (TextView)itemView.findViewById(R.id.form_title_text);
        circleIndicator = (TextView)itemView.findViewById(R.id.form_title_sub_text);
        cardFrame = (CardView)itemView.findViewById(R.id.card_frame);

        cardFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("form_id",formDisplayObject.getFormId());
                context.startActivity(intent);
            }
        });

    }

    public FormDisplayObject getFormDisplayObject() {
        return formDisplayObject;
    }

    public void setFormDisplayObject(FormDisplayObject formDisplayObject) {
        this.formDisplayObject = formDisplayObject;
    }

    public TextView getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(TextView formTitle) {
        this.formTitle = formTitle;
    }

    public void bind(){
        formTitle.setText(formDisplayObject.getFormName());
        Log.e(getClass().getSimpleName(),formDisplayObject.getFormName().charAt(0)+"");
        circleIndicator.setText("M");
    }
}
