package com.example.emergencyaid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    static String PROFILE = "profile";
    static int PROFILE_PATIENT = 1;
    static int PROFILE_DRIVER = 2;

    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private String userID, mName, mPhone;
    TextView name, phNo;
    Button patientsReqBtn;
    int profile;

    public static void launch(Context context, int profileValue) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(PROFILE, profileValue);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        phNo = findViewById(R.id.phone);
        patientsReqBtn = findViewById(R.id.patientsReqBtn);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        getIntentExtra();
        if (profile == PROFILE_PATIENT)
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);
        else
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(userID);
        getDriverInfo();
        patientsReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile == PROFILE_DRIVER)
                    startActivity(new Intent(ProfileActivity.this, DriverMapActivity.class));
                else
                    startActivity(new Intent(ProfileActivity.this, PatientMapActivity.class));
            }
        });
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        profile = intent.getIntExtra(PROFILE, 0);
        if (profile == PROFILE_DRIVER)
            patientsReqBtn.setText("SEARCH PATIENTS");
        else patientsReqBtn.setText("SEARCH AMBULANCE");
    }

    private void getDriverInfo() {
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Name") != null) {
                        mName = map.get("Name").toString();
                        name.setText(mName);

                    }

                    if (map.get("Phone") != null) {
                        mPhone = map.get("Phone").toString();
                        phNo.setText(mPhone);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

}