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
import android.view.WindowManager;
import android.widget.Toast;

import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.OldNotes.AddNoteFragment;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    FirebaseAuth fAuth;

    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        userViewModel.getUserByEmail("g").observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "User exists", Toast.LENGTH_SHORT).show();
            }
        });
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailEntry.getText().toString();
            String password = binding.passwordEntry.getText().toString();
            fAuth = FirebaseAuth.getInstance();
        });

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailEntry.getText().toString().trim();
            String password = binding.passwordEntry.getText().toString().trim();
//
//            if (email.isEmpty()) {
//                binding.emailEntry.setError("Email is Required.");
//                return;
//            }
//
//            if (password.isEmpty()) {
//                binding.emailEntry.setError("Password is Required.");
//                return;
//            }
//
//            if (password.length() < 6) {
//                binding.emailEntry.setError("Password Must be >= 6 Characters");
//                return;
//            }
//
//            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
//                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_homeFragment);
//                } else {
//                    Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
            userViewModel.getUserByEmailAndPassword(email, password).observe(getViewLifecycleOwner(), user -> {
                if (user == null) {
                    Toast.makeText(getActivity(), "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_homeFragment);
                }
            });
        });
        binding.registerText.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_registrationFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}