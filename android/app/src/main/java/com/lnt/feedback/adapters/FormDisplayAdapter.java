package com.lnt.feedback.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lnt.feedback.R;
import com.lnt.feedback.models.FormDisplayObject;
import com.lnt.feedback.viewholders.FormDisplayViewHolder;
import com.lnt.feedback.viewholders.TextFieldViewHolder;

import java.util.ArrayList;

/**
 * Created by Shreyas on 6/1/2017.
 */

public class FormDisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<FormDisplayObject> formDisplayList;
    private Context context;

    public FormDisplayAdapter(ArrayList<FormDisplayObject> formDisplayList,Context context) {
        super();
        this.formDisplayList = formDisplayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return formDisplayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View form = inflater.inflate(R.layout.form_display_item, parent, false);
        return new FormDisplayViewHolder(form,context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FormDisplayObject form = formDisplayList.get(position);
        FormDisplayViewHolder viewHolder = (FormDisplayViewHolder)holder;
        viewHolder.setFormDisplayObject(form);
        viewHolder.bind();
    }


}
