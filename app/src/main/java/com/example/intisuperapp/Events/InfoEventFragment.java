package com.example.intisuperapp.Events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentInfoEventBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoEventFragment extends Fragment {
    private FragmentInfoEventBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.infoTextview.setText("This is the information you want to display.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
