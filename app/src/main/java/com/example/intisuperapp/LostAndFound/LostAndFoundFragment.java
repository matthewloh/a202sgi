package com.example.intisuperapp.LostAndFound;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentLostAndFoundBinding;

public class LostAndFoundFragment extends Fragment {

    private FragmentLostAndFoundBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLostAndFoundBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemfoundbtn.setOnClickListener(
                v -> NavHostFragment.findNavController(LostAndFoundFragment.this).navigate(R.id.action_lostAndFoundFragment_to_lostAndFoundReportFoundFragment)
        );
        binding.itemlostbtn.setOnClickListener(
                v -> NavHostFragment.findNavController(LostAndFoundFragment.this).navigate(R.id.action_lostAndFoundFragment_to_lostAndFoundReportLostFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}