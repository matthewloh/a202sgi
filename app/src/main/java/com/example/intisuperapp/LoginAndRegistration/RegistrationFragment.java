package com.example.intisuperapp.LoginAndRegistration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLoginBinding;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.materialButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(RegistrationFragment.this)
                            .navigate(R.id.action_registrationFragment_to_roleRegistrationFragment);
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}