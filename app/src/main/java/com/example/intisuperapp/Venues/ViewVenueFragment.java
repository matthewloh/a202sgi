package com.example.intisuperapp.Venues;

import android.animation.Animator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.example.intisuperapp.LoginAndRegistration.UserViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentViewVenueBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewVenueFragment extends Fragment {
    private FragmentViewVenueBinding binding;

    private UserViewModel userViewModel;
    private Animator currentAnimator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentViewVenueBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.hide();

        BottomNavigationView bottomNavigationView = ((MainActivity) getActivity()).findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setVisibility(View.GONE);

        ViewVenueFragmentArgs args = ViewVenueFragmentArgs.fromBundle(getArguments());
        int venueId = args.getVenueId();


        VenuesViewModel venuesViewModel = new VenuesViewModel(requireActivity().getApplication());
        venuesViewModel.getVenuesById(venueId).observe(getViewLifecycleOwner(), venue ->{
            if (venue !=null) {
                Glide.with(binding.getRoot())
                        .load(venue.get(0).getVenueImageURL())
                        .into(binding.fullScreenImageView);
            }
        });

    }

}


