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

import com.example.intisuperapp.Firebase.viewmodel.FirebaseViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.databinding.FragmentShowBookingVenuesBinding;

import java.util.List;
import java.util.Objects;

public class ShowVenuesFragment extends Fragment {

    private FragmentShowBookingVenuesBinding binding;

    LiveData<List<Venues>> venues;

    private VenuesViewModel venuesViewModel;
    private UserSharedViewModel userSharedViewModel;
    private FirebaseViewModel firebaseViewModel;

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
        firebaseViewModel = new ViewModelProvider(requireActivity()).get(FirebaseViewModel.class);
        venues = firebaseViewModel.getVenuesFromFirebase(venuesViewModel);
        venues.observe(getViewLifecycleOwner(), data -> {
            VenuesAdapter adapter = new VenuesAdapter(data);

                binding.venuesRecyclerView.setAdapter(adapter);
                binding.venuesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });

        if (Objects.equals(userSharedViewModel.getUserValue().getRole(), "lecturer")) {
            binding.addVenueButton.setVisibility(View.VISIBLE);
            binding.addVenueButton.setOnClickListener(v->{
                NavHostFragment.findNavController(ShowVenuesFragment.this).navigate(ShowVenuesFragmentDirections.actionBookingsVenuesToAddVenuesFragment());
            });
        } else {
            binding.addVenueButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        venuesViewModel.deleteAllVenues();
        binding = null;
    }
}
