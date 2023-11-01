package com.example.intisuperapp.HomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private UserSharedViewModel userSharedViewModel;

    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        // Set title to the fragment
        actionBar.setTitle("");
        userSharedViewModel = new ViewModelProvider(getActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(
                getViewLifecycleOwner(),
                user -> {
                    userId = user.getId();
                }
        );
        binding.appointmentsButton.setOnClickListener(
                v -> {
                    HomeFragmentDirections.ActionHomeFragmentToAppointmentsFragment action = HomeFragmentDirections.actionHomeFragmentToAppointmentsFragment(userId);
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(action);
                }
        );
        binding.discussionsButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_discussionsFragment);
                }
        );
        binding.lostAndFoundButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_lostAndFoundFragment);
                }
        );
        binding.eventsButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_eventsFragment);
                }
        );

        binding.goToBookingsPageBtn.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_bookingsFragment);
                }
        );

//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.home) {
//                navController.navigate(R.id.homeFragment);
//            } else if (itemId == R.id.events) {
//                navController.navigate(R.id.eventsFragment);
//            } else if (itemId == R.id.profile) {
//                navController.navigate(R.id.profileFragment);
//            } else if (itemId == R.id.notifications) {
//                navController.navigate(R.id.notificationsFragment);
//            }
//            return true;
//        });
    }
}