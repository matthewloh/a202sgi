package com.example.intisuperapp.LostAndFound;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.firestore.CollectionReference;

import com.example.intisuperapp.R;

public class LostAndFoundReportFoundFragment extends Fragment {

    private EditText editTextItemName;
    private EditText editTextContactInfo;
    private EditText editTextLastKnownLocation;
    private EditText editTextItemDescription;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadimageButton;
    private Uri imageUri;
    private Button submitButton;

    public LostAndFoundReportFoundFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == requireActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_and_found_report_found, container, false);
        editTextItemName = view.findViewById(R.id.editTextItemName2);
        editTextContactInfo = view.findViewById(R.id.editTextContactInfo2);
        editTextLastKnownLocation = view.findViewById(R.id.editTextLastLocation2);
        editTextItemDescription = view.findViewById(R.id.editTextItemDesc2);
        uploadimageButton = view.findViewById(R.id.uploadimg2btn);
        submitButton = view.findViewById(R.id.reportfoundbtn);

        uploadimageButton.setOnClickListener(v -> {
            // Open the image selection intent
            Intent getimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(getimage, PICK_IMAGE_REQUEST);
        });

        submitButton.setOnClickListener(v -> {
            String itemName = editTextItemName.getText().toString();
            String contactInfo = editTextContactInfo.getText().toString();
            String lastKnownLocation = editTextLastKnownLocation.getText().toString();
            String itemDescription = editTextItemDescription.getText().toString();

            // Check if any of the fields is empty
            if (itemName.isEmpty() || contactInfo.isEmpty() || lastKnownLocation.isEmpty() || itemDescription.isEmpty()) {
                // Send error message to the user
                Toast.makeText(requireContext(), "Please fill in all of the fields", Toast.LENGTH_SHORT).show();
            } else {
                saveData();
            }
        });

        return view;
    }

    public void saveData() {
        String itemName = editTextItemName.getText().toString();
        String contactInfo = editTextContactInfo.getText().toString();
        String lastKnownLocation = editTextLastKnownLocation.getText().toString();
        String itemDescription = editTextItemDescription.getText().toString();

        // Check if fields are empty
        if (itemName.isEmpty() || contactInfo.isEmpty() || lastKnownLocation.isEmpty() || itemDescription.isEmpty()) {
            // Display an error message
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri != null) {
            // Upload the image to Firebase Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("lostAndFoundImages")
                    .child(imageUri.getLastPathSegment());

            storageReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image upload successful
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Call your data handling logic with the image URL
                            handleData(itemName, contactInfo, lastKnownLocation, itemDescription, uri.toString());
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure of image upload
                        Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Handle case where imageUri is null
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void handleData(String itemName, String contactInfo, String lastKnownLocation, String itemDescription, String itemimageUrl) {
        //Convert Image URL to Blob
        Blob itemImageBlob = convertImageUrlToBlob(itemimageUrl);

        LostAndFoundItems lostAndFoundItem = new LostAndFoundItems(itemName, contactInfo, lastKnownLocation, itemDescription, itemimageUrl, "Found");


        // Add the LostAndFoundItems to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference lostAndFoundCollection = db.collection("lostAndFoundItems");
        DocumentReference newLostAndFoundItemRef = lostAndFoundCollection.document();

        newLostAndFoundItemRef.set(lostAndFoundItem)
                .addOnSuccessListener(aVoid -> {
                    // Handle success
                    Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Toast.makeText(getContext(), "Failed to add item. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private Blob convertImageUrlToBlob(String imageUrl) {
        try {
            // Download the image using Glide
            Bitmap bitmap = Glide.with(requireContext())
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get();

            // Convert Bitmap to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            // Create a Blob from the byte array
            return Blob.fromBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle errors appropriately
        }
    }


}