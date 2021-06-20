package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstAidMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_first_aid_main);

        CardView cardview1=findViewById (R.id.cardview1);
        cardview1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),FirstAidBasicsActivity.class);
                startActivity (intent);
            }
        });
        CardView cardview2=findViewById (R.id.cardview2);
        cardview2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),EmergencyResponseActivity.class);
                startActivity (intent);
            }
        });
        CardView cardview3=findViewById (R.id.cardview3);
        cardview3.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CommonInHomeActivity.class);
                startActivity (intent);
            }
        });
        CardView cardview4=findViewById (R.id.cardview4);
        cardview4.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),SeriousIncedentsActivity.class);
                startActivity (intent);
            }
        });
        CardView cardview5=findViewById (R.id.cardview5);
        cardview5.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),CommonConditionsActivity.class);
                startActivity (intent);
            }
        });
        CardView cardview6=findViewById (R.id.cardview6);
        cardview6.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),RoadIncedentsActivity.class);
                startActivity (intent);
            }
        });
    }
}