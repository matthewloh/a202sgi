package com.example.intisuperapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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

        View view = inflater.inflate(R.layout.fragment_create_bookings, container, false);
        chooseStartTime = view.findViewById(R.id.bookingStartTime);
        chooseEndTime = view.findViewById(R.id.bookingEndTime);
        addBookingButton =view.findViewById(R.id.add_booking_btn);
        chooseDate = view.findViewById(R.id.bookingDate);



        chooseVenueSpinner = view.findViewById(R.id.booking_venue_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.venue_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseVenueSpinner.setAdapter(adapter);

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(); // Show the DatePicker when the EditText is clicked
            }
        });




        chooseStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(chooseStartTime);
            }
        });

        chooseEndTime.setOnClickListener(v -> showTimePicker(chooseEndTime));

        addBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Toast message when the "Done" button is clicked
                Toast.makeText(requireContext(), "Booking Added", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showDatePicker() {
        final Calendar currentDate = Calendar.getInstance();
        year = currentDate.get(Calendar.YEAR);
        month = currentDate.get(Calendar.MONTH);
        day = currentDate.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Set the chosen date in the format "dd/MM/yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                calendar = Calendar.getInstance();
                calendar.set(selectedYear, selectedMonth, selectedDay);
                String formattedDate = sdf.format(calendar.getTime());
                chooseDate.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }



    private void showTimePicker(final EditText timeEditText) {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR);
        currentMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                timeEditText.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }



}