package com.lnt.feedback.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lnt.feedback.R;
import com.lnt.feedback.models.RatingField;
import com.lnt.feedback.models.TextField;
import com.lnt.feedback.models.SpinnerChoice;
import com.lnt.feedback.util.DataSaveUtil;
import com.lnt.feedback.viewholders.RatingTypeViewHolder;
import com.lnt.feedback.viewholders.SpinnerTypeViewHolder;
import com.lnt.feedback.viewholders.TextFieldViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class FormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> formObjectList = new ArrayList<>();
    private Context context;
    private static final int EDIT_TEXT = 1;
    private static final int RATING_TYPE = 2;
    private static final int SPINNER_TYPE = 3;

    public FormAdapter(Context context,List<Object> formObjectList) {
        super();
        this.context = context;
        this.formObjectList = formObjectList;
        DataSaveUtil.dataMap.clear();
    }

    @Override
    public int getItemViewType(int position) {

        if(formObjectList.get(position) instanceof TextField){
            return EDIT_TEXT;
        }
        else if(formObjectList.get(position) instanceof RatingField){
            return RATING_TYPE;
        }
        else if(formObjectList.get(position) instanceof SpinnerChoice){
            return SPINNER_TYPE;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case EDIT_TEXT :
                View text = inflater.inflate(R.layout.text_field_format, parent, false);
                return new TextFieldViewHolder(text);
            case RATING_TYPE:
                View rating = inflater.inflate(R.layout.rating_format,parent,false);
                return new RatingTypeViewHolder(rating);
            case SPINNER_TYPE:
                View spinner = inflater.inflate(R.layout.spinner_format,parent,false);
                return new SpinnerTypeViewHolder(spinner,context);
            default:
                break;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return formObjectList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case EDIT_TEXT:
                TextFieldViewHolder viewHolder = (TextFieldViewHolder)holder;
                viewHolder.setTextField((TextField)formObjectList.get(position));
                viewHolder.bind();
                break;
            case RATING_TYPE:
                RatingTypeViewHolder viewHolder1 = (RatingTypeViewHolder)holder;
                viewHolder1.setRatingField((RatingField)formObjectList.get(position));
                viewHolder1.bind();
                break;
            case SPINNER_TYPE:
                SpinnerTypeViewHolder viewHolder2 = (SpinnerTypeViewHolder)holder;
                viewHolder2.setSpinnerChoice((SpinnerChoice)formObjectList.get(position));
                viewHolder2.bind();
                break;
        }


    }


}
