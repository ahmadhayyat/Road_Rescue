package com.example.emergencyaid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalPatientsReq extends AppCompatActivity {
    RecyclerView patientsRv;
    PatientsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_patients_req);
        patientsRv = findViewById(R.id.patientsRv);
        patientsRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientsAdapter(this);
        patientsRv.setAdapter(adapter);
    }
}