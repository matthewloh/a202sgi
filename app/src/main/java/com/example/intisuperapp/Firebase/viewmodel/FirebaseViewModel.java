package com.example.intisuperapp.Firebase.viewmodel;

import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intisuperapp.Firebase.repo.FirebasePhotoRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class FirebaseViewModel extends ViewModel implements FirebasePhotoRepository.OnDataUploaded {
    private FirebasePhotoRepository mFirebasePhotoRepository;

    private MutableLiveData<Task<DocumentReference>> mTaskMutableLiveData = new MutableLiveData<>();

    public FirebaseViewModel() {
        mFirebasePhotoRepository = new FirebasePhotoRepository(this);
        mTaskMutableLiveData = new MutableLiveData<>();
    }

    public void uploadImagesToFirebase(Uri uri, PhotoViewModel photoViewModel) {
        mFirebasePhotoRepository.uploadImage(uri, photoViewModel);
    }

    public void getImagesFromFirebase(PhotoViewModel photoViewModel) {
        mFirebasePhotoRepository.getImages(photoViewModel);
    }

    public MutableLiveData<Task<DocumentReference>> getTaskMutableLiveData() {
        return mTaskMutableLiveData;
    }

    @Override
    public void onDataUpload(Task<DocumentReference> task) {
        mTaskMutableLiveData.setValue(task);
    }
}
