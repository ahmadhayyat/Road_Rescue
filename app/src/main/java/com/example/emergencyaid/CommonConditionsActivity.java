package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommonConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_common_conditions);

        TextView txtfever=findViewById (R.id.txtfever);
        txtfever.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),FeverActivity.class);
                startActivity (intent);
            }
        });
        TextView txtthroat=findViewById (R.id.txtthroat);
        txtthroat.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),SoreThroatActivity.class);
                startActivity (intent);
            }
        });
        TextView txtnose=findViewById (R.id.txtnose);
        txtnose.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),NoseBleedActivity.class);
                startActivity (intent);
            }
        });
        TextView txtmotion=findViewById (R.id.txtmotion);
        txtmotion.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),MotionSicknessActivity.class);
                startActivity (intent);
            }
        });
        TextView txthighbloodpressure=findViewById (R.id.txthighbloodpresure);
        txthighbloodpressure.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),HighBloodPressureActivity.class);
                startActivity (intent);
            }
        });
        TextView txtpanic=findViewById (R.id.txtpanic);
        txtpanic.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),PanicAttackActivity.class);
                startActivity (intent);
            }
        });
    }
}