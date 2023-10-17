package com.example.intisuperapp.Bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.intisuperapp.LoginAndRegistration.RegistrationFragment;
import com.example.intisuperapp.databinding.FragmentBookingsBinding;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;


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
<<<<<<<<< Temporary merge branch 1:app/src/main/java/com/example/intisuperapp/BookingsFragment.java



=========
>>>>>>>>> Temporary merge branch 2:app/src/main/java/com/example/intisuperapp/Bookings/BookingsFragment.java
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