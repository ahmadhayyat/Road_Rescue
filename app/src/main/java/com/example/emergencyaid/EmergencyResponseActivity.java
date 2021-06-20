package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EmergencyResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_emergency_response);
        TextView txtchoking=findViewById (R.id.txtchoking);
        txtchoking.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),ChokingActivity.class);
                startActivity (intent);
            }
        });
        TextView txtabc=findViewById (R.id.txtabc);
        txtabc.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),TheABCsofFirstAidActivity.class);
                startActivity (intent);
            }
        });
        TextView txtcprandaed=findViewById (R.id.txtcprandaed);
        txtcprandaed.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),BasicCPRandAEDActivity.class);
                startActivity (intent);
            }
        });
        TextView txtsignsofheartattack=findViewById (R.id.txtsignsofheartattack);
        txtsignsofheartattack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),SignsofHeartAttackActivity.class);
                startActivity (intent);
            }
        });
        TextView txtshock=findViewById (R.id.txtshock);
        txtshock.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),ManagingShockActivity.class);
                startActivity (intent);
            }
        });
        TextView txtambulance=findViewById (R.id.txtambulance);
        txtambulance.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CallingEmergencyNumberActivity.class);
                startActivity (intent);
            }
        });
    }
}