package com.example.intisuperapp.Bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentBookingsBinding;


public class BookingsFragment extends Fragment {

    private FragmentBookingsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createABookingButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(BookingsFragment.this)
                            .navigate(R.id.action_bookingsFragment_to_createBookings);
                }
        );

        binding.seeAllText.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(BookingsFragment.this)
                            .navigate(R.id.action_bookingsFragment_to_showAllBookings);
                }
        );
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}