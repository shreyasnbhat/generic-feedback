package com.lnt.feedback.activities;

import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lnt.feedback.R;
import com.lnt.feedback.adapters.FormAdapter;
import com.lnt.feedback.models.SpinnerChoice;
import com.lnt.feedback.util.DataSaveUtil;

import java.util.ArrayList;
import java.util.List;

public class FormSetupActivity extends AppCompatActivity {

    private TextInputEditText userIdEditText;
    private DatabaseReference mFirebaseUsersReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference mFirebaseSessionFormRef = FirebaseDatabase.getInstance().getReference().child("Forms");
    private DatabaseReference userRef;
    private DatabaseReference sessionRef;
    private ValueEventListener mValueEventListener;
    private ValueEventListener sessionListValueEventListener;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView numberTextView;
    private Button submitButton;
    private Button confirmDetailButton;
    private TextView detailTextView;
    private CardView profileCard,spinnerCard;
    private android.support.v7.widget.AppCompatSpinner materialSpinner;

    String selectedSession;


    private String collegeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_setup);

        //Reference IDs
        userIdEditText = (TextInputEditText)findViewById(R.id.user_id);
        nameTextView = (TextView)findViewById(R.id.name);
        emailTextView = (TextView)findViewById(R.id.email);
        numberTextView = (TextView)findViewById(R.id.mobile_number);
        submitButton = (Button)findViewById(R.id.submit_id);
        confirmDetailButton = (Button)findViewById(R.id.confirm_details);
        detailTextView = (TextView)findViewById(R.id.details);
        profileCard = (CardView)findViewById(R.id.card_details);
        spinnerCard = (CardView)findViewById(R.id.session_select_card);
        materialSpinner = (android.support.v7.widget.AppCompatSpinner)findViewById(R.id.material_spinner);


        detailTextView.setBackgroundColor(getIntent().getIntExtra("color",0));
        confirmDetailButton.setVisibility(View.INVISIBLE);
        profileCard.setVisibility(View.INVISIBLE);
        spinnerCard.setVisibility(View.INVISIBLE);



        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                profileCard.setVisibility(View.VISIBLE);
                String name = dataSnapshot.child("Name").getValue(String.class);
                String mobile = dataSnapshot.child("Mobile Number").getValue(String.class);
                String email = dataSnapshot.child("Email").getValue(String.class);
                collegeCode = dataSnapshot.child("College Code").getValue(String.class);
                Log.e("Check",name + " " + mobile + " " + email);
                nameTextView.setText(name);
                numberTextView.setText(mobile);
                emailTextView.setText(email);
                confirmDetailButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        sessionListValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spinnerCard.setVisibility(View.VISIBLE);
                ArrayList<String> choice = new ArrayList<String>();
                Log.e("Data",dataSnapshot.getChildren()+"");
                for (DataSnapshot childShot : dataSnapshot.child("choices").getChildren()) {
                    choice.add(childShot.getValue(String.class));
                }
                Log.e("Choics",choice+"");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FormSetupActivity.this,
                        R.layout.spinner_adapter_item, choice);
                materialSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSession = materialSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSession = "Other";
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIdEditText.getText().toString();
                Log.e("User ID",userID);
                userRef = mFirebaseUsersReference.child(userID).child("0");
                sessionRef = mFirebaseSessionFormRef.child(getIntent().getStringExtra("form_id")).child("0");
                userRef.addValueEventListener(mValueEventListener);
                sessionRef.addValueEventListener(sessionListValueEventListener);
            }
        });

        confirmDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FormSetupActivity.this,FormActivity.class);
                i.putExtra("name",nameTextView.getText().toString());
                i.putExtra("mobile",numberTextView.getText().toString());
                i.putExtra("email",emailTextView.getText().toString());
                i.putExtra("form_id",getIntent().getStringExtra("form_id"));
                i.putExtra("college code",collegeCode);
                i.putExtra("ID",userIdEditText.getText().toString());
                i.putExtra("session_name",selectedSession);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(userRef!=null) {
            userRef.removeEventListener(mValueEventListener);
        }

    }
}
