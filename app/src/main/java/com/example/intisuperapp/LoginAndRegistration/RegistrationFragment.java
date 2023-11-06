package com.example.intisuperapp.LoginAndRegistration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;


    private UserViewModel userViewModel;

    private UserSharedViewModel userSharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);

        binding.proceedToRoleButton.setOnClickListener(v -> {
            String fullName = binding.fullnameText.getText().toString();
            String email = binding.emailText.getText().toString();
            String password = binding.password.getText().toString();
            RegistrationFragmentDirections.ActionRegistrationFragmentToRoleRegistrationFragment action = RegistrationFragmentDirections.actionRegistrationFragmentToRoleRegistrationFragment(
                    fullName, email, password
            );
            NavHostFragment.findNavController(RegistrationFragment.this)
                    .navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}