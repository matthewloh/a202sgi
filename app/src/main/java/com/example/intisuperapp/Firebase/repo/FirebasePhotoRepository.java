package com.example.intisuperapp.Firebase.repo;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.Appointments.AppointmentViewModel;
import com.example.intisuperapp.Firebase.model.Photo;
import com.example.intisuperapp.Firebase.viewmodel.PhotoViewModel;
import com.example.intisuperapp.Venues.Venues;
import com.example.intisuperapp.Venues.VenuesViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FirebasePhotoRepository {
    private FirebaseFirestore mFirebaseFirestore;

    private StorageReference mStorageReference;

    private OnDataUploaded onDataUploaded;

    public FirebasePhotoRepository(OnDataUploaded onDataUploaded) {
        this.onDataUploaded = onDataUploaded;
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference().child("photos");
    }

    public void uploadImage(Uri uri, PhotoViewModel photoViewModel) {
        StorageReference photoRef = mStorageReference.child(String.valueOf(System.currentTimeMillis()));
        photoRef.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.isComplete()) {
                    photoRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                        Photo photo = new Photo();
                        photo.setImageUrl(uri1.toString());
                        mFirebaseFirestore.collection("images").add(photo).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                photoViewModel.insertPhoto(photo);
                            }
                            onDataUploaded.onDataUpload(task1);
                        });
//                                task1 -> {
//                                    if (task1.isComplete()) {
//                                        photoViewModel.insertPhoto(photo);
//                                    }
//                                }
                    });
                }
            }
        });

    }

    public void uploadImageToAppointments(Uri uri, AppointmentViewModel appointmentViewModel, Appointment appointment) {
        StorageReference photoRef = mStorageReference.child(String.valueOf(System.currentTimeMillis()));
        photoRef.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.isComplete()) {
                    photoRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                        appointment.setImageUrl(uri1.toString());
                        mFirebaseFirestore.collection("images").add(appointment).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                appointmentViewModel.insert(appointment);
                            }
                            onDataUploaded.onDataUpload(task1);
                        });
//                                task1 -> {
//                                    if (task1.isComplete()) {
//                                        photoViewModel.insertPhoto(photo);
//                                    }
//                                }
                    });
                }
            }
        });

    }

    public void uploadImageToVenues(Uri uri, VenuesViewModel venuesViewModel, Venues venues) {
        StorageReference photoRef = mStorageReference.child(String.valueOf(System.currentTimeMillis()));
        photoRef.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.isComplete()) {
                    photoRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                        venues.setVenueImageURL(uri1.toString());
                        mFirebaseFirestore.collection("venues").add(venues).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                venuesViewModel.insert(venues);
                            }
                            onDataUploaded.onDataUpload(task1);
                        });
                    });
                }}});}

    public void getImages(PhotoViewModel photoViewModel) {
        mFirebaseFirestore.collection("images").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Photo photo = doc.getDocument().toObject(Photo.class);
                        photoViewModel.insertPhoto(photo);
                    }
                }
            }
        });
    }

    public LiveData<List<Venues>> getVenues(VenuesViewModel venuesViewModel) {
        mFirebaseFirestore.collection("venues").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Venues venues = doc.getDocument().toObject(Venues.class);
                        venuesViewModel.insert(venues);
                    }
                }
            }
        });
        return venuesViewModel.getAllVenues();
    }

    public void deleteVenue(VenuesViewModel venuesViewModel, String venueName) {
        mFirebaseFirestore.collection("venues").document(venueName).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                venuesViewModel.deleteVenuesByName(venueName);
            }
        });
    }


    public interface OnDataUploaded {
        void onDataUpload(Task<DocumentReference> task);
    }
}
