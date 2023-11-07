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
import com.example.intisuperapp.databinding.FragmentRoleRegistrationBinding;

public class RoleRegistrationFragment extends Fragment {

    private FragmentRoleRegistrationBinding binding;

    private UserViewModel userViewModel;
    private UserSharedViewModel userSharedViewModel;

    private int userId;

    private String fullName;
    private String email;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoleRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoleRegistrationFragmentArgs args = RoleRegistrationFragmentArgs.fromBundle(getArguments());
        fullName = args.getFullName();
        email = args.getEmail();
        password = args.getPassword();
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        binding.studentButton.setOnClickListener(v -> {
            // Create user
            User newUser = new User(fullName, email, password, "student");
            userViewModel.insert(newUser);
            // Set user
            userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    userSharedViewModel.setUser(user);
                    NavHostFragment.findNavController(RoleRegistrationFragment.this)
                            .navigate(R.id.action_roleRegistrationFragment_to_homeFragment);
                }
            });
        });
        binding.lecturerButton.setOnClickListener(v -> {
            // Create user
            User newUser = new User(fullName, email, password, "lecturer");
            userViewModel.insert(newUser);
            // Set user
            userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    userSharedViewModel.setUser(user);
                    NavHostFragment.findNavController(RoleRegistrationFragment.this)
                            .navigate(R.id.action_roleRegistrationFragment_to_homeFragment);
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}