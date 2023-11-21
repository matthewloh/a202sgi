package com.example.intisuperapp.HomePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
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
//        ((HideShowIconInterface) getActivity()).showHamburgerIcon();
//        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(
                getViewLifecycleOwner(),
                user -> userId = user.getId()
        );

        binding.welcomeText.setText("Welcome, " + userSharedViewModel.getUser().getValue().getFullname() );
        binding.appointmentsButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_appointmentsFragment);
                }
        );
        binding.bookingsButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_bookingsFragment);
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
                            .navigate(R.id.action_homeFragment_to_eventMainFragment);
                }
        );


    }
}