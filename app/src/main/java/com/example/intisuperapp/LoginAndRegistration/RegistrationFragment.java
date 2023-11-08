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
import android.widget.Toast;

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
//        binding.materialButton.setOnClickListener(
//                v -> {
//                    String email = binding.editTextEmailAddress.getText().toString().trim();
//                    String password = binding.passwordEntry.getText().toString().trim();
//                    String confirmPassword = binding.editTextConfirmPass.getText().toString().trim();
//                    String fullName = binding.editTextFullName.getText().toString().trim();
//
//
//                    if(TextUtils.isEmpty(email)){
//                        binding.editTextEmailAddress.setError("Email is Required.");
//                        return;
//                    }
//
//                    if(TextUtils.isEmpty(password)){
//                        binding.editTextConfirmPass.setError("Password is Required.");
//                        return;
//                    }
//
//                    if(password.length() < 6){
//                        binding.passwordEntry.setError("Password must have more than 6 characters");
//                        return;
//                    }
//
//                    if(TextUtils.isEmpty(confirmPassword)){
//                        binding.editTextConfirmPass.setError("Confirm Password is Required.");
//                        return;
//                    }
//
//                    if(TextUtils.isEmpty(fullName)){
//                        binding.editTextFullName.setError("Full Name is Required.");
//                        return;
//                    }
//
//                    if(!TextUtils.equals(password, confirmPassword)){
//                        binding.editTextConfirmPass.setError("Passwords do not match.");
//                        return;
//                    }
//
//                    binding.progressBar.setVisibility(View.VISIBLE);
//
//                    fAuth.createUserWithEmailAndPassword(email, confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(requireContext(), "User Created.", Toast.LENGTH_SHORT).show();
//                                NavHostFragment.findNavController(RegistrationFragment.this)
//                                        .navigate(R.id.action_registrationFragment_to_roleRegistrationFragment);
//                            }else {
//                                Toast.makeText(requireContext(), "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                binding.progressBar.setVisibility(View.GONE);
//                                NavHostFragment.findNavController(RegistrationFragment.this)
//                                        .navigate(R.id.action_registrationFragment_to_loginFragment);
//                            }
//                        }
//                    });
//                }
//        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}