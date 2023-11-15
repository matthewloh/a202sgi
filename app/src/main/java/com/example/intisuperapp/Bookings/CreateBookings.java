package com.example.intisuperapp.Bookings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentCreateBookingsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CreateBookings extends Fragment {

    private FragmentCreateBookingsBinding binding;
    private UserSharedViewModel userSharedViewModel;

    private BookingsViewModel bookingsViewModel;
    EditText chooseStartTime, chooseEndTime, chooseDate, contactInput;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int year, month, day;
    String amPm;
    Spinner chooseVenueSpinner;
    Button addBookingButton;

    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingsViewModel = new ViewModelProvider(getActivity()).get(BookingsViewModel.class);
        userSharedViewModel = new ViewModelProvider(getActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                userId = user.getId();
            }
        });

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("");
        chooseStartTime = binding.bookingStartTime;
        chooseEndTime = binding.bookingEndTime;
        addBookingButton = binding.addBookingBtn;
        chooseDate = binding.bookingDate;
        contactInput = binding.bookingContact;


        chooseVenueSpinner = binding.bookingVenueSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.venue_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseVenueSpinner.setAdapter(adapter);

        chooseDate.setOnClickListener(v -> showDatePicker());

        chooseStartTime.setOnClickListener(v -> showTimePicker(chooseStartTime));

        chooseEndTime.setOnClickListener(v -> showTimePicker(chooseEndTime));

        addBookingButton.setOnClickListener(v -> {
            if (chooseDate.getText().toString().isEmpty() || chooseStartTime.getText().toString().isEmpty() || chooseEndTime.getText().toString().isEmpty() || contactInput.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            } else if (chooseStartTime.getText().toString().compareTo(chooseEndTime.getText().toString()) >= 0) {
                Toast.makeText(requireContext(), "Please choose a valid time", Toast.LENGTH_SHORT).show();
            } else if (chooseVenueSpinner.getSelectedItem().toString().equals("Please select")) {
                Toast.makeText(requireContext(), "Please select a venue", Toast.LENGTH_SHORT).show();
            } else if (contactInput.getText().toString().length() <= 8 || contactInput.getText().toString().length() >= 12) {
                Toast.makeText(requireContext(), "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
            } else {
                bookingsViewModel.getBookingsByDateVenue(
                                chooseVenueSpinner.getSelectedItem().toString(),
                                chooseDate.getText().toString())
                        .observe(getViewLifecycleOwner(), overlappedBookingsDateVenue -> {
                            if (overlappedBookingsDateVenue != null) {
                                if (isVenueDateTimeRangeOverlapping(overlappedBookingsDateVenue, chooseVenueSpinner.getSelectedItem().toString(), chooseDate.getText().toString(), chooseStartTime.getText().toString(), chooseEndTime.getText().toString())) {
                                    Toast.makeText(requireContext(), "Booking already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // add to database
                                    String venue = chooseVenueSpinner.getSelectedItem().toString();
                                    String date = chooseDate.getText().toString();
                                    String strStartTime = chooseStartTime.getText().toString();
                                    String strEndTime = chooseEndTime.getText().toString();
                                    String contact = contactInput.getText().toString();

                                    Bookings bookings = new Bookings(venue, date, strStartTime, strEndTime, contact, userId);
                                    bookingsViewModel.insert(bookings);

                                    Toast.makeText(requireContext(), "Booking Added", Toast.LENGTH_SHORT).show();

                                    NavHostFragment.findNavController(CreateBookings.this)
                                            .navigate(R.id.action_createBookings_to_bookingsFragment);
                                }


                            }
                        });
            }
        });

        binding.showVenueText.setOnClickListener(v -> {
            NavHostFragment.findNavController(CreateBookings.this)
                    .navigate(R.id.action_createBookings_to_bookingsVenues);
        });
    }

    private void showDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        year = currentDate.get(Calendar.YEAR);
        month = currentDate.get(Calendar.MONTH);
        day = currentDate.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);

            if (selectedDate.before(currentDate)) {
                Toast.makeText(requireContext(), "Please choose an upcoming date", Toast.LENGTH_SHORT).show();
            } else {
                String formattedDate = sdf.format(selectedDate.getTime());
                chooseDate.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }


    private void showTimePicker(final EditText timeEditText) {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR);
        currentMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedTime.set(Calendar.MINUTE, minute);

            if (hourOfDay >= 12) {
                amPm = "PM";
            } else {
                amPm = "AM";
            }
            String formattedTime = sdf.format(selectedTime.getTime());
            timeEditText.setText(formattedTime);

        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

    private boolean isDateVenueOverlapping(List<Bookings> bookings, String selectedVenue, String selectedDate) {
        for (Bookings booking : bookings) {
            if (booking.getVenue().equals(selectedVenue) && booking.getDate().equals(selectedDate)) {
                return true; // Overlapping date and venue found
            }
        }
        return false; // No overlapping date and venue
    }


    private boolean isVenueDateTimeRangeOverlapping(List<Bookings> bookings, String selectedVenue, String selectedDate, String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);

        try {
            Date userStartTime = sdf.parse(startTime);
            Date userEndTime = sdf.parse(endTime);

            for (Bookings booking : bookings) {
                if (booking.getVenue().equals(selectedVenue) && booking.getDate().equals(selectedDate)) {
                    Date bookingStartTime = sdf.parse(booking.getStartTime());
                    Date bookingEndTime = sdf.parse(booking.getEndTime());

                    if (userStartTime.before(bookingEndTime) && userEndTime.after(bookingStartTime)) {
                        return true; // Overlapping date, venue, and time range found
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false; // No overlapping date, venue, and time range
    }

}