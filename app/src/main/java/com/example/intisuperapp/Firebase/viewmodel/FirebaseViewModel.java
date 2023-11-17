package com.example.intisuperapp.Firebase.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.Appointments.AppointmentViewModel;
import com.example.intisuperapp.Firebase.repo.FirebasePhotoRepository;
import com.example.intisuperapp.Venues.Venues;
import com.example.intisuperapp.Venues.VenuesViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

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

    public void uploadImagesToAppointment(Uri uri, AppointmentViewModel appointmentViewModel, Appointment appointment) {
        mFirebasePhotoRepository.uploadImageToAppointments(uri, appointmentViewModel, appointment);
    }

    public void uploadImagesToVenues(Uri uri, VenuesViewModel venuesViewModel, Venues venue) {
        mFirebasePhotoRepository.uploadImageToVenues(uri, venuesViewModel, venue);
    }
    public void getImagesFromFirebase(PhotoViewModel photoViewModel) {
        mFirebasePhotoRepository.getImages(photoViewModel);
    }

    public LiveData<List<Venues>> getVenuesFromFirebase(VenuesViewModel venuesViewModel) {
        return mFirebasePhotoRepository.getVenues(venuesViewModel);

    }

    public void deleteVenueFromFirebase(VenuesViewModel venuesViewModel, String venueName) {
        mFirebasePhotoRepository.deleteVenue(venuesViewModel, venueName);
    }



    public MutableLiveData<Task<DocumentReference>> getTaskMutableLiveData() {
        return mTaskMutableLiveData;
    }

    @Override
    public void onDataUpload(Task<DocumentReference> task) {
        mTaskMutableLiveData.setValue(task);
    }


}
