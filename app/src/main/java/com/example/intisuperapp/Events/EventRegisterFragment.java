package com.example.intisuperapp.Events;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentEventRegisterBinding;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


public class EventRegisterFragment extends Fragment {

    private FragmentEventRegisterBinding binding;

    public EventRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get event details from arguments
        Bundle arguments = getArguments();
        if (arguments != null) {
            String eventName = arguments.getString("eventName");
            String eventId = arguments.getString("eventId");

            // Check if eventId is not null before using it
            if (eventId != null) {
                // Fetch event details from Firestore based on eventId
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("events")
                        .document(eventId)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Document found, get eventImageURL
                                    String eventImageURL = document.getString("eventImageURL");

                                    // Log eventImageURL to check if it's retrieved correctly
                                    Log.d("EventRegisterFragment", "EventImageURL: " + eventImageURL);

                                    // Load event image using Glide
                                    Glide.with(requireContext())
                                            .load(eventImageURL)
                                            .into(binding.eventimg);

                                    // Set other event details (e.g., event name, date, etc.)
                                    // ...
                                } else {
                                    // Document not found
                                    Log.e("EventRegisterFragment", "Event document not found");
                                }
                            } else {
                                // Handle error
                                Log.e("EventRegisterFragment", "Error fetching event details", task.getException());
                            }
                        });
            } else {
                // Handle the case where eventId is null
                Log.e("EventRegisterFragment", "EventId is null");
            }

            // Set click listeners
            binding.backbtn.setOnClickListener(v -> navigateToEventMainFragment());
            binding.eventinfobtn.setOnClickListener(v -> navigateToEventInfoFragment(eventName));
        }
    }




    private void navigateToEventMainFragment() {
        // Navigate back to EventMainFragment
        NavHostFragment.findNavController(this).popBackStack();
    }

    private void navigateToEventInfoFragment(String eventName) {
        // Navigate to EventInfoFragment and pass the event name as an argument
        Bundle bundle = new Bundle();
        bundle.putString("eventName", eventName);
        NavHostFragment.findNavController(this).navigate(R.id.action_eventRegisterFragment_to_eventInfoFragment, bundle);
    }
}