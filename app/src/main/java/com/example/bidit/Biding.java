package com.example.bidit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Biding extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<Integer> dropDownValue = new ArrayList<Integer>();

    Spinner mSpinner;
    String label="";
    String sellerId,productId,imageUrl,pName;
    TextView mOutputSpinnerTv;
    TextView increase_value,cost;
    FirebaseAuth mAuth;
    FirebaseUser user;
    int buyers_premium=0;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    String bidPlaced;
    private List<Upload> mUploads;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    Spinner spinner;
    ProgressBar progressBar;
    DatabaseReference mDatabasePlayers;
    int increased_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biding);
        Bundle extras = getIntent().getExtras();

        mDatabasePlayers = FirebaseDatabase.getInstance().getReference().child("Bids");

        progressBar = findViewById(R.id.progressBar);
        increase_value = findViewById(R.id.buyers_premium);
        cost = findViewById(R.id.total_cost);



        //user = mAuth.getCurrentUser();



        if (extras.getString("pPrice") != "") {

            spinner =  findViewById(R.id.spinner);
            mSpinner = findViewById(R.id.spinner);
            String priceOfProduct = extras.getString("pPrice").trim();
            final int p = Integer.parseInt(priceOfProduct);
            sellerId = extras.getString("sellerId").trim();
            productId = extras.getString("productId").trim();
            imageUrl = extras.getString("imageUrl").trim();
            pName = extras.getString("pName").trim();



            final ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dropDownValue);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Spinner click listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
                    int index = spinner.getSelectedItemPosition();
                    label = arg0.getItemAtPosition(arg2).toString();

                    if(index>=0)
                    {

                        int selectedVal = Integer.parseInt(label);
                        buyers_premium = (int) (selectedVal*10/100);
                        //Toast.makeText(Biding.this, "Selected: " + buyers_premium, Toast.LENGTH_LONG).show();
                        int total_cost = buyers_premium + selectedVal;
                        increase_value.setText(String.valueOf(buyers_premium));
                        cost.setText(String.valueOf(total_cost));

                    }
                }
                public void onNothingSelected(AdapterView<?>arg0) {}
            });
            Query queryRef = mDatabasePlayers.orderByChild("productId").equalTo(productId).limitToLast(1);
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String bidplace = postSnapshot.child("bidPlaced").getValue().toString();
                            dropDownValues(Integer.parseInt(bidplace));
                        }


                        spinner.setAdapter(aa);
                    }
                    else {

                        progressBar.setVisibility(View.VISIBLE);

                        dropDownValues(p);
                    }

                    spinner.setAdapter(aa);


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Biding.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }
        }


        public void saveBid(View view){


            final TextView input = new TextView (Biding.this);
            final AlertDialog.Builder alert = new AlertDialog.Builder(Biding.this);
            alert.setTitle("User details!");
            alert.setMessage(
                    "By clicking agree you are accepting the terms of service."

            );

            alert.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do something with value!

                    String status ="Pending";
                    progressBar.setVisibility(View.VISIBLE);
                    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String text = spinner.getSelectedItem().toString();
                    DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
                    Date date = new Date();
                    String strDate = dateFormat.format(date).toString();
                    Bid bid = new Bid(sellerId,text,currentUserId,productId, strDate,imageUrl,status,pName);

                    String keyid = mDatabasePlayers.push().getKey();
                    //mDatabasePlayers.child(keyid).setValue(bid); //adding user info to database

                    mDatabasePlayers.child(keyid).setValue(bid)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Do what you need to do

                                    Toast.makeText(Biding.this,"Bid placed!", Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(Biding.this,BottomNavigation.class);
                                    startActivity(intent);
                                }
                            });
                }
            });

            alert.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do something with value!

                }
            });
            alert.show();



        }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public double dropDownValues(int value){


        increased_value = (int) (value*10/100);

        for(int i=0;i<4;i++){

            value = value + increased_value;

            dropDownValue.add(value);


        }

        return value;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}