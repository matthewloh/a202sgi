package com.example.intisuperapp.Firebase.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.intisuperapp.Firebase.model.Photo;

import java.util.List;

@Dao
public interface PhotoDao {
    @Insert
    void insertPhoto(Photo photo);

    @Query("SELECT * FROM photo_table")
    LiveData<List<Photo>> getAllPhotos();

    // Get photo id
    @Query("SELECT id FROM photo_table ORDER BY id DESC LIMIT 1")
    int getPhotoId();

}
