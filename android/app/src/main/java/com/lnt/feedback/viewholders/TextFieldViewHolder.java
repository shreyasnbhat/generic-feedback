package com.lnt.feedback.viewholders;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lnt.feedback.R;
import com.lnt.feedback.models.TextField;
import com.lnt.feedback.util.DataSaveUtil;

import static com.lnt.feedback.util.DataSaveUtil.dataMap;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class TextFieldViewHolder extends RecyclerView.ViewHolder {

    private TextField textField;

    private TextView description;
    private TextInputEditText entry;

    public TextFieldViewHolder(View itemView) {
        super(itemView);

        description = (TextView)itemView.findViewById(R.id.description);
        entry = (TextInputEditText)itemView.findViewById(R.id.edit_text_normal);

        //Add a textChangeListener to capture values of the editText
        entry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Storing Form Data Statically
                dataMap.put(description.getText().toString(),s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Empty
            }
        });
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public TextInputEditText getEntry() {
        return entry;
    }

    public void setEntry(TextInputEditText entry) {
        this.entry = entry;
    }

    public void bind(){

        description.setText(textField.getTitle());
        entry.setHint(textField.getDescription());

        if(DataSaveUtil.dataMap.get(description.getText().toString()) != null){
            entry.setText(DataSaveUtil.dataMap.get(description.getText().toString()));
        }
        else{
            entry.setText("");
        }

        if(textField.getInputType()!=-1) {
            entry.setInputType(textField.getInputType());
        }

    }

}
