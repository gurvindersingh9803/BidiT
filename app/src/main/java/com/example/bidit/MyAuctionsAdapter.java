package com.example.bidit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAuctionsAdapter extends RecyclerView.Adapter<MyAuctionsAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Bid> mBids;
    String condition = null;
    int conditionColor;
    public MyAuctionsAdapter(Context context, List<Bid> bids) {
        this.mContext = context;
        this.mBids = bids;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_my_auction, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final Bid bidcurrent = mBids.get(position);

        Log.i("biddddddddd",bidcurrent.getStatus());




        holder.textViewbidplace.setText("Bid placed-:" + bidcurrent.getBidPlaced());
        holder.textViewdate.setText("placed on-:" + bidcurrent.getDate());
        holder.textDesc.setText(bidcurrent.getpName());
        holder.status.setText(bidcurrent.getStatus());

        Picasso.get()
                .load(bidcurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,String.valueOf(uploadCurrent.getKey()),Toast.LENGTH_SHORT).show();

                Context context = view.getContext();


              //  Intent intent = new Intent(context,ProductDetails.class);
                //intent.putExtra("image_url",uploadCurrent.getImageUrl());
                //intent.putExtra("price",uploadCurrent.getpPrice());
                //intent.putExtra("sellerId",uploadCurrent.getSellerId());
                //intent.putExtra("productId",uploadCurrent.getKey());





              //  context.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return mBids.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewbidplace,textViewdate,status,textDesc;
        public ImageView imageView;
        RelativeLayout relative_layout;


        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewbidplace = itemView.findViewById(R.id.bidPlaced);
            textViewdate = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);

            textDesc = itemView.findViewById(R.id.text_view_name);


            imageView = itemView.findViewById(R.id.image_view_upload);
            relative_layout = itemView.findViewById(R.id.relative_layout);

        }

    }
}
