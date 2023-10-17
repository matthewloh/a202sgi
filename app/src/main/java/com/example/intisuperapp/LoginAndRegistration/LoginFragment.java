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

import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.OldNotes.AddNoteFragment;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.button.setOnClickListener(
                v -> {

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