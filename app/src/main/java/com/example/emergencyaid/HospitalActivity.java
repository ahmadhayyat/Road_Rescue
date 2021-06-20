package com.example.emergencyaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HospitalActivity extends AppCompatActivity {
    private EditText mNameField, mPhoneField, mAddressField, mVacantField;

    private Button mBack, mConfirm, patientsReqBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mHospitalDatabase;
    private AwesomeValidation awesomeValidation;
    private static final Pattern Name =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter & digits
                    ".+" +               //at least 1 characters
                    "$");
    private static final Pattern address =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z0-9])" +      //any letter & digits
                    ".+" +               //at least 1 characters
                    "$");
    private static final Pattern Phone =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    ".{10,}" +               //at least 10 characters
                    "$");
    private static final Pattern Vacant =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    ".+" +               //at least 1 characters
                    "$");
    private String userID;
    private String mName;
    private String mPhone;
    private String mAddress;
    private String mVacant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mNameField = (EditText) findViewById(R.id.hospitalName);
        awesomeValidation.addValidation(this, R.id.hospitalName, Name, R.string.error_field_required);

        mAddressField = (EditText) findViewById(R.id.address);
        awesomeValidation.addValidation(this, R.id.address, address, R.string.error_field_required);

        mPhoneField = (EditText) findViewById(R.id.phone);
        awesomeValidation.addValidation(this, R.id.phone, Phone, R.string.error_invalid_no);

        mVacantField = (EditText) findViewById(R.id.vacant);
        awesomeValidation.addValidation(this, R.id.vacant, Vacant, R.string.error_field_required);

        mBack = (Button) findViewById(R.id.back);
        mConfirm = (Button) findViewById(R.id.confirm);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mHospitalDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Hospitals").child(userID);

        getUserInfo();

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    saveUserInformation();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        patientsReqBtn = findViewById(R.id.patientsReqBtn);
        patientsReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalActivity.this, HospitalPatientsReq.class));
            }
        });
    }

    private void getUserInfo() {
        mHospitalDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("Name") != null) {
                        mName = map.get("Name").toString();
                        mNameField.setText(mName);

                    }

                    if (map.get("Phone") != null) {
                        mPhone = map.get("Phone").toString();
                        mPhoneField.setText(mPhone);

                    }
                    if (map.get("Address") != null) {
                        mAddress = map.get("Address").toString();
                        mAddressField.setText(mAddress);

                    }

                    if (map.get("Vacant") != null) {
                        mVacant = map.get("Vacant").toString();
                        mVacantField.setText(mVacant);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void saveUserInformation() {
        mName = mNameField.getText().toString();
        mPhone = mPhoneField.getText().toString();
        mVacant = mVacantField.getText().toString();
        mAddress = mAddressField.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("Name", mName);
        userInfo.put("Phone", mPhone);
        userInfo.put("Address", mAddress);
        userInfo.put("Vacant", mVacant);
        mHospitalDatabase.updateChildren(userInfo);
        Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show();
    }

}