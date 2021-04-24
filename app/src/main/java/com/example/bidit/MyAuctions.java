package com.example.bidit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MyAuctions extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAuctionsAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Bid> mBids;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.my_auctions, container, false);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {


            mRecyclerView = v.findViewById(R.id.recycler_view2);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mProgressCircle = v.findViewById(R.id.progress_circle);
            mBids = new ArrayList<>();
            mAdapter = new MyAuctionsAdapter(getActivity(), mBids);
            mRecyclerView.setAdapter(mAdapter);
            mStorage = FirebaseStorage.getInstance();

            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Bids");
            Query queryRef = mDatabaseRef.orderByChild("currentUserId").equalTo(currentUser.getEmail());

            mDBListener = queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mBids.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Bid bid = postSnapshot.getValue(Bid.class);
                        bid.setKey(postSnapshot.getKey());
                        //bid.getProductId(postSnapshot.getKey())

                        mBids.add(bid);
                        mBids.add(0,bid);

                        mAdapter.notifyDataSetChanged();
                    }
                    mProgressCircle.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
            });

        } else {

            Toast.makeText(getActivity(), "Please login first", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), UserLogin.class);
            startActivity(intent);

        }

        return v;


    }





}



