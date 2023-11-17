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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.Firebase.viewmodel.FirebaseViewModel;
import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.LoginAndRegistration.UserViewModel;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.Venues.Venues;
import com.example.intisuperapp.Venues.VenuesViewModel;
import com.example.intisuperapp.databinding.FragmentUpdateBookingsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class UpdateBookings extends Fragment {

    EditText chooseStartTime, chooseEndTime, chooseDate, contactInput;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int year, month, day;
    String amPm;
    Spinner chooseVenueSpinner;
    Button saveBookingButton;
    private FragmentUpdateBookingsBinding binding;
    private UserViewModel userViewModel;
    private FirebaseViewModel firebaseViewModel;
    private VenuesViewModel venuesViewModel;
    final List<String> venueList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUpdateBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venuesViewModel = new ViewModelProvider(getActivity()).get(VenuesViewModel.class);
        firebaseViewModel = new ViewModelProvider(getActivity()).get(FirebaseViewModel.class);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");

        UpdateBookingsArgs args = UpdateBookingsArgs.fromBundle(getArguments());
        int bookingId = args.getBookingId();
        int userId = args.getUserId();
        String bookingDate = args.getBookingDate();
        String bookingStartTime = args.getBookingStartTime();
        String bookingEndTime = args.getBookingEndTime();
        String bookingContact = args.getBookingContact();
        String bookingVenue = args.getBookingVenue();

        chooseVenueSpinner = binding.bookingVenueSpinner;

        venueList.add("Please select");

        firebaseViewModel.getVenuesFromFirebase(venuesViewModel).observe(getViewLifecycleOwner(), venues -> {
            if (venues != null) {
                for (Venues venue : venues) {
                    venueList.add(venue.getVenueName());
                }

                // Create and set the adapter after obtaining the venue list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, venueList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                chooseVenueSpinner.setAdapter(adapter);
            }
        });

        BookingsViewModel bookingsViewModel = new ViewModelProvider(getActivity()).get(BookingsViewModel.class);
        bookingsViewModel.getBookingsById(bookingId).observe(getViewLifecycleOwner(), bookings -> {
            // Set the EditTexts to the values of the selected Bookings object
            binding.bookingDate.setText(bookingDate);
            binding.bookingStartTime.setText(bookingStartTime);
            binding.bookingEndTime.setText(bookingEndTime);
            binding.bookingContact.setText(bookingContact);
//
            binding.bookingVenueSpinner.setSelection(venueList.indexOf(bookingVenue));

        });

        chooseDate = binding.bookingDate;
        chooseStartTime = binding.bookingStartTime;
        chooseEndTime = binding.bookingEndTime;
        contactInput = binding.bookingContact;
        saveBookingButton = binding.saveBookingBtn;

        chooseDate.setOnClickListener(v -> {
            showDatePicker();
        });

        chooseStartTime.setOnClickListener(v -> {
            showTimePicker(chooseStartTime);
        });

        chooseEndTime.setOnClickListener(v -> {
            showTimePicker(chooseEndTime);
        });

        saveBookingButton.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.US);
            Date startTime = formatter.parse(chooseStartTime.getText().toString(), new java.text.ParsePosition(0));
            Date endTime = formatter.parse(chooseEndTime.getText().toString(), new java.text.ParsePosition(0));

            if (chooseDate.getText().toString().isEmpty() || chooseStartTime.getText().toString().isEmpty() || chooseEndTime.getText().toString().isEmpty() || contactInput.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                assert startTime != null && endTime != null;
                if (endTime.before(startTime) || startTime.equals(endTime)) {
                    Toast.makeText(requireContext(), "Please choose a valid time", Toast.LENGTH_SHORT).show();
                } else if (chooseVenueSpinner.getSelectedItem().toString().equals("Please select")) {
                    Toast.makeText(requireContext(), "Please select a venue", Toast.LENGTH_SHORT).show();
                } else if (contactInput.getText().toString().length() <= 8 || contactInput.getText().toString().length() >= 12) {
                    Toast.makeText(requireContext(), "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
                } else {

                    String contact = contactInput.getText().toString();
                    String venue = chooseVenueSpinner.getSelectedItem().toString();
                    String strDate = chooseDate.getText().toString();
                    String strStartTime = chooseStartTime.getText().toString();
                    String strEndTime = chooseEndTime.getText().toString();


                    bookingsViewModel.updateBookingsById(venue, strDate, strStartTime, strEndTime, contact, userId, bookingId);
                    Toast.makeText(requireContext(), "Booking updated", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(UpdateBookings.this)
                            .navigate(R.id.action_updateBookings_to_bookingsFragment);
                }
            }
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

    @Override
    public void onDestroyView() {
        venueList.clear();
        super.onDestroyView();
        venuesViewModel.deleteAllVenues();
        binding = null;
    }
}



