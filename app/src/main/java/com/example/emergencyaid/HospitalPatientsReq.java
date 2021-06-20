package com.example.emergencyaid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class HospitalPatientsReq extends AppCompatActivity {
    static String KEY_IS_FROM_HOSPITAL = "isFromHospital";
    static String KEY_VACANT = "Vacant";
    static String KEY_NAME = "Name";
    static String KEY_ADDRESS = "Address";
    static String KEY_PHONE = "Phone";
    static String KEY_HOSPITAL_ID = "HospitalId";
    static String KEY_DRIVER_NAME = "driverName";
    static String KEY_DRIVER_ID = "driverId";
    static String KEY_REQ_ID = "reqId";
    static String KEY_STATUS = "Status";
    static String KEY_PENDING = "Pending";
    static String KEY_DECLINED = "Rejected";
    static String KEY_APPROVED = "Approved";
    RecyclerView patientsRv;
    PatientsAdapter adapter;
    boolean isFromHospital;
    ArrayList<JSONObject> hospitalsList;
    JSONObject jsonData;
    ImageView refresh;
    ProgressBar prgBar;

    public static void Launch(Context context, boolean isFromHospital) {
        Intent intent = new Intent(context, HospitalPatientsReq.class);
        intent.putExtra(KEY_IS_FROM_HOSPITAL, isFromHospital);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_patients_req);
        getIntentExtra();
        hospitalsList = new ArrayList<>();
        jsonData = new JSONObject();
        patientsRv = findViewById(R.id.patientsRv);
        prgBar = findViewById(R.id.prgBar);
        initRecyclerView();
        if (!isFromHospital)
            loadHospitalsForDriver();
        else loadPatientsForHospitals();

        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }

        });

    }

    private void refreshData() {
        hospitalsList = new ArrayList<>();
        jsonData = new JSONObject();
        initRecyclerView();
        if (!isFromHospital)
            loadHospitalsForDriver();
        else loadPatientsForHospitals();
    }

    private void initRecyclerView() {
        patientsRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientsAdapter(this, hospitalsList, isFromHospital);
        patientsRv.setAdapter(adapter);
    }

    private void loadHospitalsForDriver() {
        prgBar.setVisibility(View.VISIBLE);
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String[] hospitalId = new String[1];
        DatabaseReference mHospitalDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Hospitals");
        mHospitalDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    prgBar.setVisibility(View.GONE);
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        hospitalId[0] = s.getKey();
                        jsonData = new JSONObject();
                        Map<String, Object> map = (Map<String, Object>) s.getValue();
                        if (map.get(KEY_VACANT) != null) {
                            int vacant = Integer.parseInt(map.get(KEY_VACANT).toString());
                            if (vacant != 0) {
                                try {
                                    jsonData.put(KEY_NAME, map.get(KEY_NAME).toString());
                                    jsonData.put(KEY_ADDRESS, map.get(KEY_ADDRESS).toString());
                                    jsonData.put(KEY_PHONE, map.get(KEY_PHONE).toString());
                                    jsonData.put(KEY_HOSPITAL_ID, hospitalId[0]);
                                    hospitalsList.add(jsonData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    addReqData(hospitalId[0], userId);
                    adapter.notifyDataSetChanged();
                } else prgBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    void addReqData(final String hospitalId, final String userId) {
        prgBar.setVisibility(View.VISIBLE);
        DatabaseReference mReqDatabase = FirebaseDatabase.getInstance().
                getReference().child("PatientsReq").child(hospitalId);
        mReqDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    prgBar.setVisibility(View.GONE);
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        String reqId = s.getKey();
                        jsonData = new JSONObject();
                        Map<String, Object> map = (Map<String, Object>) s.getValue();
                        Log.i("DATAA", map.toString());
                        if (map.get(KEY_DRIVER_ID) != null) {
                            String drvId = map.get(KEY_DRIVER_ID).toString();
                            if (drvId.equals(userId)) {
                                try {
                                    jsonData.put(KEY_NAME, map.get(KEY_NAME).toString());
                                    //jsonData.put(KEY_NAME, "myyy");
                                    jsonData.put(KEY_ADDRESS, map.get(KEY_ADDRESS).toString());
                                    //jsonData.put(KEY_ADDRESS, "addd");
                                    //jsonData.put(KEY_PHONE, "phhhh");
                                    jsonData.put(KEY_PHONE, map.get(KEY_PHONE).toString());
                                    jsonData.put(KEY_HOSPITAL_ID, hospitalId);
                                    jsonData.put(KEY_STATUS, map.get(KEY_STATUS));
                                    hospitalsList.add(jsonData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else prgBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void loadPatientsForHospitals() {
        prgBar.setVisibility(View.VISIBLE);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mReqDatabase = FirebaseDatabase.getInstance().getReference().child("PatientsReq").child(userId);
        mReqDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    prgBar.setVisibility(View.GONE);
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        String reqId = s.getKey();
                        jsonData = new JSONObject();
                        Map<String, Object> map = (Map<String, Object>) s.getValue();
                        if (map.get(KEY_STATUS) != null) {
                            String status = map.get(KEY_STATUS).toString();
                            if (status.equals(KEY_PENDING)) {
                                try {
                                    jsonData.put(KEY_STATUS, status);
                                    jsonData.put(KEY_DRIVER_NAME, map.get(KEY_DRIVER_NAME).toString());
                                    jsonData.put(KEY_REQ_ID, reqId);
                                    hospitalsList.add(jsonData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    prgBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        isFromHospital = intent.getBooleanExtra(KEY_IS_FROM_HOSPITAL, false);
    }
}