package com.example.intisuperapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.intisuperapp.LoginAndRegistration.RegistrationFragment;
import com.example.intisuperapp.databinding.FragmentHomeBinding;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;



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


        binding.goToBookingsPageBtn.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_bookingsFragment);
                }
        );
    }
}