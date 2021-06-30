package com.example.emergencyaid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.emergencyaid.HospitalPatientsReq.KEY_ADDRESS;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_APPROVED;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_DECLINED;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_DRIVER_ID;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_DRIVER_NAME;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_HOSPITAL_ID;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_NAME;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_PENDING;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_PHONE;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_REQ_ID;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_STATUS;
import static com.example.emergencyaid.HospitalPatientsReq.KEY_VACANT;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {
    LayoutInflater mInflater;
    Boolean isFromHospital;
    ArrayList<JSONObject> hospitalsList;
    Context context;
    final int min = 2;
    final int max = 10;

    public PatientsAdapter(Context context, ArrayList<JSONObject> hospitalsList, boolean isFromHospital) {
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.isFromHospital = isFromHospital;
        this.hospitalsList = hospitalsList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.hospital_patient_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull @NotNull final ViewHolder holder, int position) {
        if (isFromHospital) {
            holder.llHospitalName.setVisibility(View.GONE);
            holder.llHospitalAddress.setVisibility(View.GONE);
            holder.reqBtn.setVisibility(View.GONE);
            holder.llVacEst.setVisibility(View.GONE);
        } else {
            holder.llDriver.setVisibility(View.GONE);
            holder.llHospitalBtn.setVisibility(View.GONE);
        }

        final JSONObject jsonObject = hospitalsList.get(position);
        if (!isFromHospital) {
            try {
                holder.hospitalName.setText(jsonObject.getString(KEY_NAME));
                holder.address.setText(jsonObject.getString(KEY_ADDRESS));
                holder.vacantTv.setText(jsonObject.getString(KEY_VACANT));
                int random = new Random().nextInt((max - min) + 1) + min;
                holder.estTv.setText(random + " mins");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (jsonObject.has(KEY_STATUS)) {
                    if (jsonObject.get(KEY_STATUS).equals(KEY_PENDING)) {
                        holder.statusTv.setText(KEY_PENDING);
                        holder.statusTv.setBackgroundColor(Color.parseColor("#FF9800"));
                        holder.reqBtn.setEnabled(false);
                        holder.reqBtn.setBackgroundColor(context.getColor(android.R.color.darker_gray));
                        holder.statusTv.setVisibility(View.VISIBLE);
                    } else if (jsonObject.get(KEY_STATUS).equals(KEY_APPROVED)) {
                        holder.statusTv.setText(KEY_APPROVED);
                        holder.statusTv.setBackgroundColor(context.getColor(android.R.color.holo_green_dark));
                        holder.reqBtn.setEnabled(false);
                        holder.reqBtn.setBackgroundColor(context.getColor(android.R.color.darker_gray));
                        holder.statusTv.setVisibility(View.VISIBLE);
                    } else if (jsonObject.get(KEY_STATUS).equals(KEY_DECLINED)) {
                        holder.statusTv.setText(KEY_DECLINED);
                        holder.statusTv.setBackgroundColor(context.getColor(android.R.color.holo_red_dark));
                        holder.reqBtn.setEnabled(false);
                        holder.reqBtn.setBackgroundColor(context.getColor(android.R.color.darker_gray));
                        holder.statusTv.setVisibility(View.VISIBLE);
                    }
                }
                holder.reqBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final DatabaseReference patientsReq = FirebaseDatabase.getInstance().getReference().child("PatientsReq");
                        DatabaseReference driversReq = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(userId);
                        final String requestId = patientsReq.push().getKey();
                        driversReq.addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                    if (map.get("Name") != null) {
                                        try {
                                            String name = map.get("Name").toString();
                                            Log.i("DATAA", "driver name " + name);
                                            HashMap hashMap = new HashMap();
                                            hashMap.put(KEY_DRIVER_NAME, name);
                                            hashMap.put(KEY_STATUS, KEY_PENDING);
                                            hashMap.put(KEY_DRIVER_ID, userId);
                                            hashMap.put(KEY_NAME, jsonObject.get(KEY_NAME));
                                            hashMap.put(KEY_ADDRESS, jsonObject.get(KEY_ADDRESS));
                                            hashMap.put(KEY_PHONE, jsonObject.get(KEY_PHONE));

                                            patientsReq.child(jsonObject.getString(KEY_HOSPITAL_ID)).child(requestId).updateChildren(hashMap);
                                            holder.reqBtn.setEnabled(false);
                                            holder.reqBtn.setBackgroundColor(context.getColor(android.R.color.darker_gray));
                                            holder.statusTv.setVisibility(View.VISIBLE);
                                            ((Activity) context).finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                holder.driverName.setText(jsonObject.getString(KEY_DRIVER_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            holder.acceptBtn.setOnClickListener(onAccDecBtnClick(KEY_APPROVED, jsonObject));
            holder.declinedBtn.setOnClickListener(onAccDecBtnClick(KEY_DECLINED, jsonObject));
        }
    }

    private View.OnClickListener onAccDecBtnClick(final String status, final JSONObject jsonObject) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                try {
                    final DatabaseReference patientsReq = FirebaseDatabase.getInstance().getReference().
                            child("PatientsReq").child(userId).child(jsonObject.getString(KEY_REQ_ID));
                    HashMap hashMap = new HashMap();
                    hashMap.put(KEY_STATUS, status);
                    patientsReq.updateChildren(hashMap);
                    ((Activity) context).finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return hospitalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llDriver, llHospitalName, llHospitalAddress, llHospitalBtn, llVacEst;
        Button declinedBtn, acceptBtn, reqBtn;
        TextView hospitalName, address, driverName, statusTv, vacantTv, estTv;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            llDriver = itemView.findViewById(R.id.llDriver);
            llHospitalName = itemView.findViewById(R.id.llHospitalName);
            llHospitalAddress = itemView.findViewById(R.id.llHospitalAddress);
            llHospitalBtn = itemView.findViewById(R.id.llHospitalBtn);
            declinedBtn = itemView.findViewById(R.id.declinedBtn);
            acceptBtn = itemView.findViewById(R.id.acceptBtn);
            reqBtn = itemView.findViewById(R.id.patientReqBtn);
            hospitalName = itemView.findViewById(R.id.hospitalName);
            address = itemView.findViewById(R.id.hospitalAddress);
            driverName = itemView.findViewById(R.id.driverName);
            statusTv = itemView.findViewById(R.id.statusTv);
            llVacEst = itemView.findViewById(R.id.llHospitalVacEst);
            vacantTv = itemView.findViewById(R.id.vacant);
            estTv = itemView.findViewById(R.id.est);
        }
    }
}
