package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RoadIncedentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_road_incedents);

        TextView txtresponder=findViewById (R.id.txtresponder);
        txtresponder.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),FirstStepsofaFirstResponderActivity.class);
                startActivity (intent);
            }
        });
        TextView txtkit=findViewById (R.id.txtkit);
        txtkit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),KeepaUsefulKitintheVehicleActivity.class);
                startActivity (intent);
            }
        });
        TextView txtcallambulance=findViewById (R.id.txtcallambulance);
        txtcallambulance.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CallingEmergencyNumberActivity.class);
                startActivity (intent);
            }
        });
    }
}