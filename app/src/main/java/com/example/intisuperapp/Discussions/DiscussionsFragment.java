package com.example.intisuperapp.Discussions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.intisuperapp.databinding.FragmentDiscussionsBinding;

public class DiscussionsFragment extends Fragment {

    private FragmentDiscussionsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscussionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Insert code here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}