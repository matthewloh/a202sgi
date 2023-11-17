package com.example.intisuperapp.LoginAndRegistration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.databinding.FragmentRegistrationBinding;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    private UserViewModel userViewModel;
    boolean isEmailValid = false;

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

        binding.proceedToRoleButton.setOnClickListener(v -> {
            String fullName = binding.fullnameText.getText().toString();
            String email = binding.emailText.getText().toString();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();
            if (isValidated(fullName, email, password, confirmPassword)) {
                String bcryptHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                RegistrationFragmentDirections.ActionRegistrationFragmentToRoleRegistrationFragment action = RegistrationFragmentDirections.actionRegistrationFragmentToRoleRegistrationFragment(fullName, email, bcryptHash);
                NavHostFragment.findNavController(RegistrationFragment.this).navigate(action);
            }
        });
    }

    public boolean isValidated(String fullName, String email, String password, String confirmPassword) {
        if (fullName.isEmpty()) {
            binding.fullnameText.setError("Full name is required");
            Toast.makeText(requireContext(), "Full name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            binding.emailText.setError("Email is required");
            Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check email ends in @student.newinti.edu.my or @newinti.edu.my
        if (!email.endsWith("@student.newinti.edu.my") && !email.endsWith("@newinti.edu.my")) {
            binding.emailText.setError("Please enter a valid INTI email");
            Toast.makeText(requireContext(), "Please enter a valid INTI email", Toast.LENGTH_SHORT).show();
            return false;
        }
        userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.emailText.setError("Email already exists");
                Toast.makeText(requireContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                isEmailValid = false;
            } else {
                isEmailValid = true;
            }
        });
        if (!isEmailValid) {
            return false;
        }
        if (password.isEmpty()) {
            binding.password.setError("Password is required");
            Toast.makeText(requireContext(), "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            binding.password.setError("Password must have more than 6 characters");
            Toast.makeText(requireContext(), "Password must have more than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.contains(" ")) {
            binding.password.setError("Password cannot contain spaces");
            Toast.makeText(requireContext(), "Password cannot contain spaces", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPassword.isEmpty()) {
            binding.confirmPassword.setError("Confirm password is required");
            Toast.makeText(requireContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            binding.confirmPassword.setError("Passwords do not match");
            Toast.makeText(requireContext(), "Password do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}