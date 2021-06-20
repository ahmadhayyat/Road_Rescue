package com.example.emergencyaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SeriousIncedentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_serious_incedents);

        TextView txtheartattack=findViewById (R.id.txtheartattack);
        txtheartattack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),HeartAttackActivity.class);
                startActivity (intent);
            }
        });
        TextView txtbleeding=findViewById (R.id.txtbleeding);
        txtbleeding.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),BleedingActivity.class);
                startActivity (intent);
            }
        });
        TextView txtstroke=findViewById (R.id.txtstroke);
        txtstroke.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),StrokeActivity.class);
                startActivity (intent);
            }
        });
        TextView txtpoisoning=findViewById (R.id.txtpoisoning);
        txtpoisoning.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),PoisoningActivity.class);
                startActivity (intent);
            }
        });
        TextView txtburning=findViewById (R.id.txtburning);
        txtburning.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent (getApplicationContext (),BurningActivity.class);
                startActivity (intent);
            }
        });
    }
}