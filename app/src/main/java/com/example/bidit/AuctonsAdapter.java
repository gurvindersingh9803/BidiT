package com.example.bidit;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuctonsAdapter extends RecyclerView.Adapter<AuctonsAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> auctionsFull;

    private List<Upload> mUploads;
    public AuctonsAdapter(Context context, List<Upload> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
        auctionsFull = new ArrayList<>(mUploads);

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.single_auction_view, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {

        final Upload uploadCurrent = mUploads.get(position);

        String inputDateString =  uploadCurrent.getDateTime();
        Calendar calCurr = Calendar.getInstance();
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(new SimpleDateFormat("MM/dd/yyyy h:mm a").parse(inputDateString));
            if(day.before(calCurr) || inputDateString == ""){
                holder.itemView.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.height = 0;
                params.width = 0;
                holder.itemView.setLayoutParams(params);
                holder.itemView.setVisibility(View.VISIBLE);


            }else{

                holder.textViewName.setText(uploadCurrent.getName());
                holder.textViewPrice.setText("$CAD " +  uploadCurrent.getpPrice());
                holder.textViewShortDescription.setText(uploadCurrent.getpDesc());

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,String.valueOf(uploadCurrent.getKey()),Toast.LENGTH_SHORT).show();

                Context context = view.getContext();


                Intent intent = new Intent(context,ProductDetails.class);
                intent.putExtra("image_url",uploadCurrent.getImageUrl());
                intent.putExtra("price",uploadCurrent.getpPrice());
                intent.putExtra("sellerId",uploadCurrent.getSellerId());
                intent.putExtra("productId",uploadCurrent.getKey());
                intent.putExtra("imageUrl",uploadCurrent.getImageUrl());
                intent.putExtra("date",uploadCurrent.getDateTime());
                intent.putExtra("pName",uploadCurrent.getName());
                intent.putExtra("descs",uploadCurrent.getpDesc());









                context.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName,textViewShortDescription,textViewPrice;
        public ImageView imageView;
        RelativeLayout relative_layout;
        CountDownTimer timerCount;



        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewShortDescription = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            imageView = itemView.findViewById(R.id.image_view_upload);
            relative_layout = itemView.findViewById(R.id.relative_layout);

        }

    }

    public void filter(String text) {
        auctionsFull.clear();
        if(text.isEmpty()){
            auctionsFull.addAll(mUploads);
        } else{
            Log.i("mmmmmmmmmmmmm", String.valueOf(auctionsFull));

            text = text.toLowerCase();
            for(Upload item: auctionsFull){
                if(item.getName().toLowerCase().contains(text)){
                    auctionsFull.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
