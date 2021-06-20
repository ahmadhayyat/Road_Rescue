package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommonInHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_common_in_home);

        TextView txtcuts=findViewById (R.id.txtcuts);
        txtcuts.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CutsActivity.class);
                startActivity (intent);
            }
        });
        TextView txtwounds=findViewById (R.id.txtwounds);
        txtwounds.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),PunctureWoundsActivity.class);
                startActivity (intent);
            }
        });
        TextView txtdental=findViewById (R.id.txtdental);
        txtdental.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),DentalInjuriesActivity.class);
                startActivity (intent);
            }
        });
        TextView txtdiabetic=findViewById (R.id.txtdiabetic);
        txtdiabetic.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),DiabeticEmergenciesActivity.class);
                startActivity (intent);
            }
        });
        TextView txtear=findViewById (R.id.txtear);
        txtear.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),EaracheandEarInjuryActivity.class);
                startActivity (intent);
            }
        });
        TextView txtallergic=findViewById (R.id.txtallergic);
        txtallergic.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),AllergicReactionsActivity.class);
                startActivity (intent);
            }
        });
    }
}