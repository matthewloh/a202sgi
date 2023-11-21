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

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLoginBinding;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    private UserViewModel userViewModel;

    private UserSharedViewModel userSharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        binding.emailEntry.setOnFocusChangeListener(
                (v, hasFocus) -> { // Clear hint
                    if (hasFocus) {
                        binding.emailEntry.setHint("");
                    } else {
                        binding.emailEntry.setHint("Email");
                    }
                }
        );
        binding.password.setOnFocusChangeListener(
                (v, hasFocus) -> { // Clear hint
                    if (hasFocus) {
                        binding.password.setHint("");
                    } else {
                        binding.password.setHint("Password");
                    }
                }
        );
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailEntry.getText().toString();
            String password = binding.password.getText().toString();
            if (email.isEmpty()) {
                binding.emailEntry.setError("Email is required");
                return;
            } else if (password.isEmpty()) {
                binding.password.setError("Password is required");
                return;
            }
            userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
                    Toast.makeText(getActivity(), "Account not found. Have you registered an account?", Toast.LENGTH_SHORT).show();
                } else {
                    BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (!result.verified) {
                        Toast.makeText(getActivity(), "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                        userSharedViewModel.setUser(user);
                        NavHostFragment.findNavController(LoginFragment.this).navigate(
                                R.id.action_loginFragment_to_homeFragment
                        );
                    }
                }
            });
        });
        binding.registerText.setOnClickListener(
                v -> NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registrationFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}