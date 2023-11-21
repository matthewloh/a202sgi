package com.example.intisuperapp.Firebase.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.intisuperapp.Firebase.model.Photo;
import com.example.intisuperapp.Firebase.repo.PhotoRoomRepository;

import java.util.List;

public class PhotoViewModel extends AndroidViewModel {
    private PhotoRoomRepository mRepository;

    public PhotoViewModel(Application application) {
        super(application);
        mRepository = new PhotoRoomRepository(application);
    }

    public void insertPhoto(Photo photo) {
        mRepository.insertPhoto(photo);
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mRepository.getAllPhotos();
    }

    public int getPhotoId() {
        return mRepository.getPhotoId();
    }
}
