package com.example.intisuperapp.Bookings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;
import com.example.intisuperapp.databinding.FragmentShowAllBookingsBinding;


public class ShowAllBookings extends Fragment {

    private FragmentShowAllBookingsBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowAllBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.doneBtn.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(ShowAllBookings.this)
                            .navigate(R.id.action_showAllBookings_to_bookingsFragment);
                }
        );


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
