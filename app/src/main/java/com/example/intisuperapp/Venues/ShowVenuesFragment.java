package com.example.intisuperapp.Venues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.databinding.FragmentShowBookingVenuesBinding;

import java.util.List;

public class ShowVenuesFragment extends Fragment {

    private FragmentShowBookingVenuesBinding binding;

    LiveData<List<Venues>> venues;

    private VenuesViewModel venuesViewModel;
    private UserSharedViewModel userSharedViewModel;

    public int userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShowBookingVenuesBinding.inflate(inflater, container, false);
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

        venues = venuesViewModel.getAllVenues();
        venues.observe(getViewLifecycleOwner(), data -> {
            VenuesAdapter adapter = new VenuesAdapter(data);
            adapter.setOnItemClickListener(venue ->{
                Toast.makeText(getContext(),  venue.getVenueName(), Toast.LENGTH_SHORT).show();
                ShowVenuesFragmentDirections.ActionBookingsVenuesToViewVenueFragment action = ShowVenuesFragmentDirections.actionBookingsVenuesToViewVenueFragment(venue.getVenueId());
                NavHostFragment.findNavController(ShowVenuesFragment.this).navigate(action);
            });

            adapter.setOnLongItemClickListener(venue ->{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Venue");
                builder.setMessage("Are you sure you want to delete " +  venue.getVenueName()+ " ?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    venuesViewModel.deleteVenuesById(venue.getVenueId());
                    Toast.makeText(getContext(), "Venue deleted", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                });
            });




        binding.venuesRecyclerView.setAdapter(adapter);
        binding.venuesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });




        binding.addVenueButton.setOnClickListener(v->{
            NavHostFragment.findNavController(ShowVenuesFragment.this).navigate(ShowVenuesFragmentDirections.actionBookingsVenuesToAddVenuesFragment());
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
