package com.lnt.feedback.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lnt.feedback.R;

public class AdminActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "myPrefs";
    private SharedPreferences sharedpreferences;

    private DatabaseReference mFirebaseUsersReference = FirebaseDatabase.getInstance().getReference().child("Users");

    private ValueEventListener mValueEventListener;
    private boolean checker = false;
    private android.support.design.widget.TextInputEditText UserId, Name, Email, Mobile;

    private Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button logout = (Button) findViewById(R.id.clear);
        addUser = (Button) findViewById(R.id.add_user);

        UserId = (android.support.design.widget.TextInputEditText) findViewById(R.id.user_id_edit_text);
        Name = (android.support.design.widget.TextInputEditText) findViewById(R.id.user_name_edit_text);
        Email = (android.support.design.widget.TextInputEditText) findViewById(R.id.user_email_edit_text);
        Mobile = (android.support.design.widget.TextInputEditText) findViewById(R.id.user_phone_edit_text);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("user_id", "user");
                editor.putString("user_password", "pass");
                editor.apply();
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = checkValidity();
                if (!check) {
                    Toast.makeText(AdminActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseUsersReference.child(UserId.getText().toString()).child("0").child("Name").setValue(Name.getText().toString());
                    mFirebaseUsersReference.child(UserId.getText().toString()).child("0").child("Email").setValue(Email.getText().toString());
                    mFirebaseUsersReference.child(UserId.getText().toString()).child("0").child("Mobile Number").setValue(Mobile.getText().toString());
                    Toast.makeText(AdminActivity.this, "Successfully Added a User!", Toast.LENGTH_SHORT).show();
                    clearAll();
                }
            }

        });


    }


    public boolean checkValidity() {

        if (UserId.getText().toString().isEmpty() || Name.getText().toString().isEmpty() || Mobile.getText().toString().isEmpty() || Email.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    public void clearAll() {

        UserId.setText("");
        Name.setText("");
        Email.setText("");
        Mobile.setText("");
    }
}
