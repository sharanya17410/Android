package com.mobileChallenge.ToDolApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView card1,card2,card3,card4,card5,card6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        card1 =findViewById(R.id.card1);
        card2 =findViewById(R.id.card2);
        card3 =findViewById(R.id.card3);



        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.card1: i= new Intent(this,ActivityList.class);
                            startActivity(i);
                            break;

        }

    }
}
