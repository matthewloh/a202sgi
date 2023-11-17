package com.example.intisuperapp.Bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentBookingsBinding;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class BookingsFragment extends Fragment {

    private FragmentBookingsBinding binding;
    LiveData<List<Bookings>> bookings;

    private UserSharedViewModel userSharedViewModel;
    private BookingsViewModel bookingsViewModel;

    private int userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingsViewModel = new ViewModelProvider(requireActivity()).get(BookingsViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                userId = user.getId();
                // Create the BookingsViewModel using the user's ID
                // Now, you can use the bookingsViewModel to fetch bookings.
                bookings = bookingsViewModel.getAllBookingsForUser(userId);
                bookings.observe(getViewLifecycleOwner(), bookings1 -> {
                    BookingsAdapter adapter = new BookingsAdapter(bookings1);
                    adapter.setOnItemClickListener(booking -> {

                                BookingsFragmentDirections.ActionBookingsFragmentToUpdateBookings action =
                                        BookingsFragmentDirections.actionBookingsFragmentToUpdateBookings(booking.getId(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getContact(), booking.getVenue(), userId);
                                NavHostFragment.findNavController(BookingsFragment.this).navigate(action);
                    });
                    adapter.setOnItemLongClickListener(booking -> {
                        new AlertDialog.Builder(getActivity()).setTitle("Do you want to delete Booking " + booking.getId() + "?").setPositiveButton("Yes", (dialog, which) -> {
                            bookingsViewModel.delete(booking);
                            Toast.makeText(this.getContext(), "Booking Deleted", Toast.LENGTH_SHORT).show();
                        }).setNegativeButton("No", (dialog, which) -> {

                        }).show();
                    });
                    binding.bookingRecyclerView.setAdapter(adapter);
                    binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                });
            }
        });
        binding.createABookingButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(BookingsFragment.this).navigate(R.id.action_bookingsFragment_to_createBookings);
        });

        binding.filterButton.setOnClickListener(v -> {
            String[] choices = {"None", "Venue", "Date (Ascending)", "Date (Descending)"};
            new AlertDialog.Builder(getActivity()).setTitle("Filter by:").setSingleChoiceItems(choices, 0, (dialog, which) -> {

                    }).setPositiveButton("OK", (dialog, which) -> {
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selectedPosition == 0) {
                            bookingsViewModel.getAllBookingsForUser(userId).observe(getViewLifecycleOwner(), bookings1 -> {
                                BookingsAdapter adapter = new BookingsAdapter(bookings1);
                                adapter.setOnItemClickListener(booking -> {
//                                // Navigate to the EditBookingsFragment
                                    BookingsFragmentDirections.ActionBookingsFragmentToUpdateBookings action =
                                            BookingsFragmentDirections.actionBookingsFragmentToUpdateBookings(booking.getId(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getContact(), booking.getVenue(), userId);
                                    NavHostFragment.findNavController(BookingsFragment.this).navigate(action);
                                });
                                adapter.setOnItemLongClickListener(booking -> {
                                    new AlertDialog.Builder(getActivity()).setTitle("Do you want to delete Booking " + booking.getId() + "?").setPositiveButton("Yes", (dialog_, which_) -> {
                                        bookingsViewModel.delete(booking);
                                        Toast.makeText(this.getContext(), "Booking Deleted", Toast.LENGTH_SHORT).show();
                                    }).setNegativeButton("No", (dialog_, which_) -> {
                                    }).show();
                                });
                                binding.bookingRecyclerView.setAdapter(adapter);
                                binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            });
                        } else if (selectedPosition == 1) {
                            bookingsViewModel.getBookingsByVenueAsc(userId).observe(getViewLifecycleOwner(), bookings1 -> {
                                BookingsAdapter adapter = new BookingsAdapter(bookings1);
                                adapter.setOnItemClickListener(booking -> {
//                                // Navigate to the EditBookingsFragment
                                    BookingsFragmentDirections.ActionBookingsFragmentToUpdateBookings action =
                                            BookingsFragmentDirections.actionBookingsFragmentToUpdateBookings(booking.getId(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getContact(), booking.getVenue(), userId);
                                    NavHostFragment.findNavController(BookingsFragment.this).navigate(action);
                                });
                                adapter.setOnItemLongClickListener(booking -> {
                                    new AlertDialog.Builder(getActivity()).setTitle("Do you want to delete Booking " + booking.getId() + "?").setPositiveButton("Yes", (dialog_, which_) -> {
                                        bookingsViewModel.delete(booking);
                                        Toast.makeText(this.getContext(), "Booking Deleted", Toast.LENGTH_SHORT).show();
                                    }).setNegativeButton("No", (dialog_, which_) -> {
                                    }).show();
                                });
                                binding.bookingRecyclerView.setAdapter(adapter);
                                binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            });

                        } else if (selectedPosition == 2) {
                            bookingsViewModel.getBookingsByDateAsc(userId).observe(getViewLifecycleOwner(), bookings1 -> {
                                BookingsAdapter adapter = new BookingsAdapter(bookings1);
                                adapter.setOnItemClickListener(booking -> {
//                                // Navigate to the EditBookingsFragment
                                    BookingsFragmentDirections.ActionBookingsFragmentToUpdateBookings action =
                                            BookingsFragmentDirections.actionBookingsFragmentToUpdateBookings(booking.getId(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getContact(), booking.getVenue(), userId);
                                    NavHostFragment.findNavController(BookingsFragment.this).navigate(action);
                                });
                                adapter.setOnItemLongClickListener(booking -> {
                                    new AlertDialog.Builder(getActivity()).setTitle("Do you want to delete Booking " + booking.getId() + "?").setPositiveButton("Yes", (dialog_, which_) -> {
                                        bookingsViewModel.delete(booking);
                                        Toast.makeText(this.getContext(), "Booking Deleted", Toast.LENGTH_SHORT).show();
                                    }).setNegativeButton("No", (dialog_, which_) -> {
                                    }).show();
                                });
                                binding.bookingRecyclerView.setAdapter(adapter);
                                binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            });

                        } else if (selectedPosition == 3) {
                            bookingsViewModel.getBookingsByDate(userId).observe(getViewLifecycleOwner(), bookings1 -> {
                                BookingsAdapter adapter = new BookingsAdapter(bookings1);
                                adapter.setOnItemClickListener(booking -> {
//                                // Navigate to the EditBookingsFragment
                                    BookingsFragmentDirections.ActionBookingsFragmentToUpdateBookings action =
                                            BookingsFragmentDirections.actionBookingsFragmentToUpdateBookings(booking.getId(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getContact(), booking.getVenue(), userId);
                                    NavHostFragment.findNavController(BookingsFragment.this).navigate(action);
                                });
                                adapter.setOnItemLongClickListener(booking -> {
                                    new AlertDialog.Builder(getActivity()).setTitle("Do you want to delete Booking " + booking.getId() + "?").setPositiveButton("Yes", (dialog_, which_) -> {
                                        bookingsViewModel.delete(booking);
                                        Toast.makeText(this.getContext(), "Booking Deleted", Toast.LENGTH_SHORT).show();
                                    }).setNegativeButton("No", (dialog_, which_) -> {
                                    }).show();
                                });
                                binding.bookingRecyclerView.setAdapter(adapter);
                                binding.bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            });

                        }
                    }).setNegativeButton("Cancel", (dialog, which) -> {

                    })

                    .show();


        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}