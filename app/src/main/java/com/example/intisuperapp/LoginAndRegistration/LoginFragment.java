package com.example.intisuperapp.LoginAndRegistration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fAuth = FirebaseAuth.getInstance();


        binding.loginBtn.setOnClickListener(
                v -> {
                    String email = binding.editTextTextEmailAddress.getText().toString().trim();
                    String password = binding.editTextTextPassword.getText().toString().trim();

                    if(email.isEmpty()){
                        binding.editTextTextEmailAddress.setError("Email is Required.");
                        return;
                    }

                    if(password.isEmpty()){
                        binding.editTextTextPassword.setError("Password is Required.");
                        return;
                    }

                    if(password.length() < 6){
                        binding.editTextTextPassword.setError("Password Must be >= 6 Characters");
                        return;
                    }

                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    NavHostFragment.findNavController(LoginFragment.this)
                                            .navigate(R.id.action_loginFragment_to_homeFragment);
                                }else {
                                    Toast.makeText(getActivity(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                }
        );
        binding.registerText.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_registrationFragment);
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}