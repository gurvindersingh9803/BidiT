package com.example.bidit;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class Bid {

    String sellerId,bidPlaced,currentUserId,productId,date,imageUrl,status,pName;
    private String mKey;



    public Bid() {}


    public Bid(String sellerId, String bidPlaced,String currentUserId,String productId,String date,String imageUrl, String status,String pName) {
        this.sellerId = sellerId;
        this.bidPlaced = bidPlaced;
        this.currentUserId = currentUserId;
        this.productId = productId;
        this.date = date;
        this.imageUrl = imageUrl;
        this.status = status;
        this.pName = pName;




    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBidPlaced() {
        return bidPlaced;
    }

    public void setBidPlaced(String bidPlaced) {
        this.bidPlaced = bidPlaced;
    }
}
