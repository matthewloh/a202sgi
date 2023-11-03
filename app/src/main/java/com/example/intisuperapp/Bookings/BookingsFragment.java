package com.example.intisuperapp.Bookings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.intisuperapp.LoginAndRegistration.RegistrationFragment;
import com.example.intisuperapp.LoginAndRegistration.UserViewModel;
import com.example.intisuperapp.databinding.FragmentBookingsBinding;
import com.example.intisuperapp.databinding.FragmentRegistrationBinding;
import com.example.intisuperapp.R;

import java.util.List;


public class BookingsFragment extends Fragment {

    private FragmentBookingsBinding binding;
    LiveData<List<Bookings>> bookings;

    private BookingsViewModel bookingsViewModel;

    private UserViewModel userViewModel;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        // Observe the User LiveData to get the user's ID
        userViewModel.getUserByFullName("John Doe").observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                int johnId = user.getId();

//                // Create the BookingsViewModel using the user's ID
                bookingsViewModel = new ViewModelProvider(getActivity(),
                        new BookingsViewModelFactory(getActivity().getApplication(), johnId))
                        .get(BookingsViewModel.class);

                // Now, you can use the bookingsViewModel to fetch bookings.
                bookings = bookingsViewModel.getAllBookingsForUser(johnId);
                bookings.observe(getViewLifecycleOwner(), bookings1 -> {
                    BookingsAdapter adapter = new BookingsAdapter(bookings1);
                    binding.bookingRecyclerView.setAdapter(adapter);
                    binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                });
            }
        });



        binding.createABookingButton.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(BookingsFragment.this)
                            .navigate(R.id.action_bookingsFragment_to_createBookings);
                }
        );

        binding.seeAllText.setOnClickListener(
                v -> {
                    NavHostFragment.findNavController(BookingsFragment.this)
                            .navigate(R.id.action_bookingsFragment_to_showAllBookings);
                }
        );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}