package com.example.intisuperapp.Firebase.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity(tableName = "photo_table")
public class Photo {
    @Exclude
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imageUrl;


    public Photo() {
    }

    @Exclude
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
