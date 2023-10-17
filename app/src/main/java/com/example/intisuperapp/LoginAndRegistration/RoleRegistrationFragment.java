package com.example.intisuperapp.LoginAndRegistration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentRoleRegistrationBinding;

public class RoleRegistrationFragment extends Fragment {

    private FragmentRoleRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoleRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.studentButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(RoleRegistrationFragment.this)
                    .navigate(R.id.action_roleRegistrationFragment_to_homeFragment);
        });
        binding.lecturerButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(RoleRegistrationFragment.this)
                    .navigate(R.id.action_roleRegistrationFragment_to_homeFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}