package com.example.emergencyaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HospitalLoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    Button mLogin, mRegistration, mForgetPassword;
    ProgressBar prgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                /*if (user != null && mAuth.getCurrentUser().isEmailVerified()) {
                    Intent intent = new Intent(HospitalLoginActivity.this, HospitalActivity.class);
                    startActivity(intent);
                    finish();
                }*/

            }
        };
        prgBar = findViewById(R.id.prgBar);
        mForgetPassword = findViewById(R.id.forgetPassword);
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalLoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mLogin = findViewById(R.id.login);
        mRegistration = findViewById(R.id.registration);


        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HospitalLoginActivity.this, ActivityHospitalSignUp.class);
                startActivity(intent);
            }
        });


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if (!email.equals("") || !password.equals("")) {
                    prgBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(HospitalLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(HospitalLoginActivity.this, "Hospital Sign In Error", Toast.LENGTH_SHORT).show();
                                prgBar.setVisibility(View.GONE);
                            } else if (mAuth.getCurrentUser().isEmailVerified()) {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Hospitals").child(user_id);
                                //current_user_db.setValue(true);
                                prgBar.setVisibility(View.GONE);
                                startActivity(new Intent(HospitalLoginActivity.this, HospitalActivity.class));
                                finish();
                            } else {
                                prgBar.setVisibility(View.GONE);
                                Toast.makeText(HospitalLoginActivity.this, "Please, verify your email.", Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
                } else
                    Toast.makeText(HospitalLoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);


    }
}