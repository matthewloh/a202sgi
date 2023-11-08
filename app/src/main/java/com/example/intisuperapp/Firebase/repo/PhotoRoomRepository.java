package com.example.intisuperapp.Firebase.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.Firebase.dao.PhotoDao;
import com.example.intisuperapp.Firebase.model.Photo;
import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhotoRoomRepository {
    private PhotoDao photoDao;

    private LiveData<List<Photo>> allPhotos;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public PhotoRoomRepository(Application application) {
        INTISuperappDatabase db = INTISuperappDatabase.getInstance(application);
//        photoDao = db.photoDao();
//        allPhotos = photoDao.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return allPhotos;
    }

    public void insertPhoto(Photo photo) {
        mExecutor.execute(() -> photoDao.insertPhoto(photo));
    }

    public int getPhotoId() {
        return photoDao.getPhotoId();
    }
}
