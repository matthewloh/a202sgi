package com.example.intisuperapp.Events;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentEventMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class EventMainFragment extends Fragment {

    private FragmentEventMainBinding binding;
    private ViewPager2 viewPager2;
    private ImageSliderAdapter imageSliderAdapter;
    private List<String> eventImageURLs = new ArrayList<>();
    private List<String> eventNames = new ArrayList<>();

    public EventMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = binding.viewPager2;
        imageSliderAdapter = new ImageSliderAdapter(requireContext(), eventImageURLs);

        // Set adapter to ViewPager2
        viewPager2.setAdapter(imageSliderAdapter);

        fetchEventImageURLs();
        fetchEventNames();

        // Set click listeners for each button
        binding.eventbtn1.setOnClickListener(v -> navigateToEventRegister(0));
        binding.eventbtn2.setOnClickListener(v -> navigateToEventRegister(1));
        binding.eventbtn3.setOnClickListener(v -> navigateToEventRegister(2));
    }

    private void fetchEventImageURLs() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String eventImageURL = document.getString("eventImageURL");
                            eventImageURLs.add(eventImageURL);
                        }
                        imageSliderAdapter.notifyDataSetChanged();
                    } else {
                        // Handle error
                        Log.e("EventMainFragment", "Error fetching event image URLs", task.getException());
                    }
                });
    }

    private void fetchEventNames() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String eventName = document.getString("eventName");
                            eventNames.add(eventName);
                        }
                        setButtonTexts();
                    } else {
                        // Handle error
                        Log.e("EventMainFragment", "Error fetching event names", task.getException());
                    }
                });
    }

    private void setButtonTexts() {
        if (eventNames.size() >= 3) {
            binding.eventbtn1.setText(eventNames.get(0));
            binding.eventbtn2.setText(eventNames.get(1));
            binding.eventbtn3.setText(eventNames.get(2));
        }
    }

    private void navigateToEventRegister(int position) {
        if (position < eventNames.size()) {
            String eventName = eventNames.get(position);
            // Pass the selected event name as an argument to the EventRegisterFragment
            Bundle bundle = new Bundle();
            bundle.putString("eventName", eventName);
            NavHostFragment.findNavController(this).navigate(R.id.action_eventMainFragment_to_eventRegisterFragment, bundle);
        }
    }
}

