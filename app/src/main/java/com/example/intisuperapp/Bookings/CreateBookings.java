package com.example.intisuperapp.Bookings;

import static android.text.format.DateUtils.isToday;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;

import java.util.Calendar;
import java.util.Locale;


public class CreateBookings extends Fragment {

    EditText chooseStartTime, chooseEndTime, chooseDate;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int year, month, day;
    String amPm;
    Spinner chooseVenueSpinner;
    Button addBookingButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("");
        View view = inflater.inflate(R.layout.fragment_create_bookings, container, false);
        chooseStartTime = view.findViewById(R.id.bookingStartTime);
        chooseEndTime = view.findViewById(R.id.bookingEndTime);
        addBookingButton = view.findViewById(R.id.add_booking_btn);
        chooseDate = view.findViewById(R.id.bookingDate);


        chooseVenueSpinner = view.findViewById(R.id.booking_venue_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.venue_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseVenueSpinner.setAdapter(adapter);

        chooseDate.setOnClickListener(v -> {
            showDatePicker();
        });


        chooseStartTime.setOnClickListener(v -> showTimePicker(chooseStartTime));

        chooseEndTime.setOnClickListener(v -> showTimePicker(chooseEndTime));

        addBookingButton.setOnClickListener(v -> {

            if (chooseDate.getText().toString().isEmpty() || chooseStartTime.getText().toString().isEmpty() || chooseEndTime.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(requireContext(), "Booking Added", Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(CreateBookings.this)
                        .navigate(R.id.action_createBookings_to_bookingsFragment);
            }

        });

        return view;
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
            Calendar selectedTime = Calendar.getInstance();
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedTime.set(Calendar.MINUTE, minute);

            if (hourOfDay >= 12) {
                amPm = "PM";
            } else {
                amPm = "AM";
            }
            timeEditText.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);

        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }


}