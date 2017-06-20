package com.lnt.feedback.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lnt.feedback.R;
import com.lnt.feedback.adapters.FormAdapter;
import com.lnt.feedback.models.RatingField;
import com.lnt.feedback.models.SpinnerChoice;
import com.lnt.feedback.models.TextField;
import com.lnt.feedback.util.DataSaveUtil;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseFormReference = FirebaseDatabase.getInstance().getReference().child("Forms");
    private List<Object> formList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormAdapter adapter;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_content);

        final String formId = getIntent().getStringExtra("form_id");
        Log.e("TAG", formId);

        submitButton = (Button) findViewById(R.id.submit_button);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mFirebaseFormReference.keepSynced(true);

        //Setup Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.form_recycler);
        adapter = new FormAdapter(this, formList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Dynamically Fetch Views
        mFirebaseFormReference.child(formId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                formList.clear();

                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    Log.e("INSIDE", shot.getKey());
                    String type = shot.child("type").getValue(String.class);
                    try {
                        if (type.equals("EditText")) {
                            String title = shot.child("description").getValue(String.class);
                            String hint = shot.child("description").getValue(String.class);
                            String inputType = shot.child("inputType").getValue(String.class);
                            int input = getInputTypeFromString(inputType);
                            TextField text = new TextField(title, hint, input);
                            formList.add(text);
                        } else if (type.equals("Rating")) {
                            String description = shot.child("description").getValue(String.class);
                            RatingField rating = new RatingField(description);
                            formList.add(rating);
                        } else if (type.equals("Dropdown")) {
                            String description = shot.child("description").getValue(String.class);
                            ArrayList<String> choice = new ArrayList<String>();
                            for (DataSnapshot childShot : shot.child("choices").getChildren()) {
                                choice.add(childShot.getValue(String.class));
                            }
                            SpinnerChoice spinnerChoice = new SpinnerChoice(description, choice);
                            formList.add(spinnerChoice);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Error in Firebase
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> map = DataSaveUtil.dataMap;
                String formEntreeID = "IdError";

                map.put("ID",getIntent().getStringExtra("ID"));
                map.put("Name",getIntent().getStringExtra("name"));
                map.put("College Code",getIntent().getStringExtra("college code"));

                DatabaseReference mFirebaseSubmitReference = FirebaseDatabase.getInstance().getReference().child("Submissions").child(formId);
                DatabaseReference submitReference = mFirebaseSubmitReference.child("NodeError");

                formEntreeID = map.get("ID");
                Log.e("Map",map.toString());
                submitReference = mFirebaseSubmitReference.child(formEntreeID);

                //Initialize Submission Entered Parameters
                submitReference.child("Name").setValue(map.get("Name"));
                submitReference.child("College Code").setValue(map.get("College Code"));
                submitReference.child("ID").setValue(map.get("ID"));

                //Iterate over Form Items and collect Submissions
                for (Object formItem : formList) {
                    if (formItem instanceof TextField) {
                        TextField textField = (TextField) formItem;


                        if (map.get(textField.getDescription()) != null)
                            submitReference.child(textField.getDescription()).setValue(map.get(textField.getDescription()));
                    }
                    else if (formItem instanceof SpinnerChoice) {

                        SpinnerChoice spinnerChoice = (SpinnerChoice) formItem;
                        if (map.get(spinnerChoice.getDescription()) != null) {
                            submitReference.child(spinnerChoice.getDescription()).setValue(map.get(spinnerChoice.getDescription()));
                        }
                    }
                    else if (formItem instanceof RatingField) {

                        RatingField ratingField = (RatingField) formItem;
                        if (map.get(ratingField.getDescription()) != null) {
                            submitReference.child(ratingField.getDescription()).setValue(map.get(ratingField.getDescription()));
                        }
                    }
                }

                DataSaveUtil.dataMap.clear();
                Toast.makeText(FormActivity.this,"Your Form was successfully Submitted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public int getInputTypeFromString(String type) {
        if (type == null) {
            return 1;
        }
        if (type.toLowerCase().equals("number")) {
            return 2;
        } else if (type.toLowerCase().equals("single text")) {
            return 1;
        } else if (type.toLowerCase().equals("multi text")) {
            return 131072;
        }
        return 0;
    }
}
