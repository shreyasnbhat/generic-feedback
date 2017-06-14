package com.lnt.feedback.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lnt.feedback.R;
import com.lnt.feedback.models.SpinnerChoice;
import com.lnt.feedback.util.DataSaveUtil;

/**
 * Created by Shreyas on 5/31/2017.
 */

public class SpinnerTypeViewHolder extends RecyclerView.ViewHolder {

    private SpinnerChoice spinnerChoice;

    private android.support.v7.widget.AppCompatSpinner materialSpinner;
    private TextView description;
    private Context context;

    public SpinnerTypeViewHolder(View itemView, final Context context) {
        super(itemView);
        this.context = context;
        materialSpinner = (android.support.v7.widget.AppCompatSpinner)itemView.findViewById(R.id.material_spinner);
        description = (TextView)itemView.findViewById(R.id.description);

        materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataSaveUtil.dataMap.put(description.getText().toString(),materialSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing was selected
                DataSaveUtil.dataMap.put(description.getText().toString(),"None");
            }
        });
    }

    public void setSpinnerChoice(SpinnerChoice spinnerChoice) {
        this.spinnerChoice = spinnerChoice;
    }

    public void bind(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.spinner_adapter_item, spinnerChoice.getOptions());
        materialSpinner.setAdapter(adapter);
        description.setText(spinnerChoice.getDescription());
    }
}
