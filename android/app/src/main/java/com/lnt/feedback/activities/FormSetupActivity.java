package com.lnt.feedback.activities;

import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lnt.feedback.R;
import com.lnt.feedback.util.DataSaveUtil;

public class FormSetupActivity extends AppCompatActivity {

    private TextInputEditText userIdEditText;
    private DatabaseReference mFirebaseUsersReference = FirebaseDatabase.getInstance().getReference().child("Users");
    private DatabaseReference userRef;
    private ValueEventListener mValueEventListener;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView numberTextView;
    private Button submitButton;
    private Button confirmDetailButton;
    private TextView detailTextView;

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

        detailTextView.setBackgroundColor(getIntent().getIntExtra("color",0));
        confirmDetailButton.setVisibility(View.INVISIBLE);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = userIdEditText.getText().toString();
                Log.e("User ID",userID);
                userRef = mFirebaseUsersReference.child(userID).child("0");
                userRef.addValueEventListener(mValueEventListener);
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
