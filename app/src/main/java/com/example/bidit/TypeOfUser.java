package com.example.bidit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TypeOfUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_user);
    }

    public void seller(View view){


    }

    public void buyer(View view){

        //Intent intent = new Intent(TypeOfUser.this, UserAuctionView.class);
        //startActivity(intent);
    }
}