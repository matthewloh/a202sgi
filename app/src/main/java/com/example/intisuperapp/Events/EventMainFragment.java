package com.example.intisuperapp.Events;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentEventMainBinding;


public class EventMainFragment extends Fragment {

    private FragmentEventMainBinding binding;

    public EventMainFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.eventbtn1.setOnClickListener(
                v -> NavHostFragment.findNavController(EventMainFragment.this).navigate(R.id.action_eventMainFragment_to_eventRegisterFragment)
        );
        binding.eventbtn2.setOnClickListener(
                v -> NavHostFragment.findNavController(EventMainFragment.this).navigate(R.id.action_eventMainFragment_to_eventRegisterFragment)
        );
        binding.eventbtn3.setOnClickListener(
                v -> NavHostFragment.findNavController(EventMainFragment.this).navigate(R.id.action_eventMainFragment_to_eventRegisterFragment)
        );

    }
}