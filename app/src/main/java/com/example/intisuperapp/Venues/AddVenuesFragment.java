package com.example.intisuperapp.Venues;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.Firebase.viewmodel.FirebaseViewModel;
import com.example.intisuperapp.Firebase.viewmodel.PhotoViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentAddBookingVenuesBinding;

public class AddVenuesFragment extends Fragment {
    private FragmentAddBookingVenuesBinding binding;
    private VenuesViewModel venuesViewModel;
    private UserSharedViewModel userSharedViewModel;
    private PhotoViewModel photoViewModel;
    private FirebaseViewModel firebaseViewModel;
    public int userId;

    private Uri mImageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBookingVenuesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // insert code here
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");

        venuesViewModel = new ViewModelProvider(requireActivity()).get(VenuesViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        photoViewModel = new ViewModelProvider(requireActivity()).get(PhotoViewModel.class);
        firebaseViewModel = new ViewModelProvider(requireActivity()).get(FirebaseViewModel.class);
        userId = userSharedViewModel.getUserValue().getId();

        binding.uploadImageButton.setOnClickListener(v-> {
            pickImageFromGallery.launch("image/*");
        });
        binding.addVenueBtn.setOnClickListener(v->{
            String venueName = binding.venueNameEditText.getText().toString();
            Venues venues = new Venues(venueName, "null");
            firebaseViewModel.uploadImagesToVenues(mImageUri, venuesViewModel, venues);
            firebaseViewModel.getTaskMutableLiveData().observe(getViewLifecycleOwner(), task ->{
                if (task.isSuccessful()) {
                    binding.venueImagePreview.setImageResource(R.drawable.no_image);
                    binding.venueNameEditText.setText("");
                    Toast.makeText(getContext(), "Venue Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        });
    }

    ActivityResultLauncher<String> pickImageFromGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            if (uri != null) {
                mImageUri = uri;
                binding.venueImagePreview.setImageURI(mImageUri);
            }

        }
    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
