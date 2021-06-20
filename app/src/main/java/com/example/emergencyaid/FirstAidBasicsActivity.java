package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FirstAidBasicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_first_aid_basics);

        TextView txtdontpanic=findViewById (R.id.txtdontpanic);
        txtdontpanic.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),DonotPanicActivity.class);
                startActivity (intent);
            }
        });

        TextView txtsupplies=findViewById (R.id.txtsupplies);
        txtsupplies.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),TheProperSuppliesActivity.class);
                startActivity (intent);
            }
        });

        TextView txtmedicalinfo=findViewById (R.id.txtmedicalinfo);
        txtmedicalinfo.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),GatheringMedicalInformationActivity.class);
                startActivity (intent);
            }
        });

        TextView txtemergencynmb=findViewById (R.id.txtemergencynmb);
        txtemergencynmb.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CallingEmergencyNumberActivity.class);
                startActivity (intent);
            }
        });

        TextView txtprecautions=findViewById (R.id.txtprecautions);
        txtprecautions.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),UniversalPrecautionsActivity.class);
                startActivity (intent);
            }
        });

        TextView txttraining=findViewById (R.id.txttraining);
        txttraining.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),TheProperTrainingActivity.class);
                startActivity (intent);
            }
        });
    }
}