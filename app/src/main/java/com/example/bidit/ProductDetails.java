package com.example.bidit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    ImageView imageView;
    ActionBar toolbar;
    private FirebaseAuth mAuth;
    Button b;
    DataSnapshot dataSnapshot;
    private DatabaseReference mDatabaseRef,mDatabase;
    private List<Upload> mUploads;
    private ValueEventListener mDBListener,listener;
    private FirebaseStorage mStorage;
    FirebaseAuth auth;
    String price,sellerId,productId,imageUrl,dates,ProName,p,pD,pN;
    private RecyclerView mRecyclerView;
    private AuctonsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond,prices,dscs,name,statusOfBid;
    private TextView tvEvent;
    private Handler handler;
    String url;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        txtTimerDay = (TextView) findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) findViewById(R.id.txtTimerSecond);
        tvEvent = (TextView) findViewById(R.id.time_left);
        statusOfBid = (TextView) findViewById(R.id.BidStatus);


        countDownStart();
        auth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = auth.getCurrentUser();
        imageView = findViewById(R.id.image_view);
        mAuth = FirebaseAuth.getInstance();
        b = findViewById(R.id.placeBid);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        Bundle extras = getIntent().getExtras();

        if (extras.getInt("category") >= 0) {
            url = extras.getString("image_url");
            price = extras.getString("price");
            sellerId = extras.getString("sellerId");
            productId = extras.getString("productId");
            imageUrl = extras.getString("imageUrl");
            dates = extras.getString("date");
            ProName = extras.getString("pName");
            pD = extras.getString("descs");



            name = findViewById(R.id.product_name);
            dscs = findViewById(R.id.descs);
            prices = findViewById(R.id.product_price);



            // Toast.makeText(ProductDetails.this,sellerId,Toast.LENGTH_SHORT).show();

            if (url != null) {

                Picasso.get().load(url).placeholder(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(imageView);

                name.setText(ProName);
                dscs.setText(pD);
                prices.setText("$CAD " + price);

                mRecyclerView = findViewById(R.id.recy1);
                auth = FirebaseAuth.getInstance();
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ProductDetails.this));
                mProgressCircle = findViewById(R.id.progress_circle);
                mUploads = new ArrayList<>();
                mAdapter = new AuctonsAdapter(ProductDetails.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mStorage = FirebaseStorage.getInstance();
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

                mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUploads.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            upload.setKey(postSnapshot.getKey());
                            mUploads.add(upload);
                        }
                        mAdapter.notifyDataSetChanged();
                        mProgressCircle.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });



                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bids");


                Query queryRef = ref.orderByChild("currentUserId").equalTo(currentUser.getEmail());
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()) {


                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Bids");

                                    Query queryRef1 = ref1.orderByChild("productId").equalTo(productId).limitToLast(1);


                                queryRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot2) {

                                        if (dataSnapshot2.hasChildren()) {
                                            for (DataSnapshot postSnapshot1 : dataSnapshot2.getChildren()) {
                                                String bidplace = postSnapshot1.child("bidPlaced").getValue().toString();

                                                statusOfBid.setText("You have placed  "+bidplace+" $CAD bid on this item!");

                                            }


                                        }


                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        mProgressCircle.setVisibility(View.INVISIBLE);
                                    }
                                });




                            }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });


            }
        }

        toolbar = getSupportActionBar();

        BottomNavigationView navigation =  findViewById(R.id.navigationView);
       // navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener);



    }

    public void placeBid(View view){

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            Intent intent = new Intent(ProductDetails.this,UserLogin.class);
            startActivity(intent);
        }else{

            Intent intent = new Intent(ProductDetails.this,Biding.class);
            intent.putExtra("pPrice",price);
            intent.putExtra("sellerId",sellerId);
            intent.putExtra("productId",productId);
            intent.putExtra("imageUrl",imageUrl);
            intent.putExtra("pName",ProName);





            startActivity(intent);
        }

    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "MM/dd/yyyy h:mm a");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse(dates);
                    Date currentDate = new Date();




                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));

                    } else {
                        handler.removeCallbacks(runnable);

                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event started!");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {
        findViewById(R.id.LinearLayout10).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout11).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout12).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout13).setVisibility(View.GONE);
        //findViewById(R.id.textView1).setVisibility(View.GONE);
       // findViewById(R.id.textView2).setVisibility(View.GONE);

        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference("Bids");



        Query queryRef = ref.orderByChild("productId").equalTo(productId).limitToLast(1);
        Query queryRef1 = ref.orderByChild("productId").equalTo(productId);

        if(queryRef != null) {
            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("status", "Bid Won!");
                                postSnapshot.getRef().updateChildren(map);

                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }

        if(queryRef1 != null) {
            queryRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("status", "Bid loose!");
                                ds.getRef().updateChildren(map);
                            }
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ProductDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });
        }



    }



}