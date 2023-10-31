package com.example.intisuperapp.Appointments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.intisuperapp.databinding.FragmentAddAppointmentBinding;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAppointmentFragment extends Fragment {


    private FragmentAddAppointmentBinding binding;

    private AppointmentViewModel appointmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // insert code here
        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        binding.appointmentStartDateText.setOnClickListener(view1 -> showDatePickerDialog());
        binding.appointmentEndDateText.setOnClickListener(view1 -> showDatePickerDialog());
        binding.appointmentCreateButton.setOnClickListener(view1 -> {
            int userId = AddAppointmentFragmentArgs.fromBundle(getArguments()).getUserId();
            String title = binding.appointmentsTitle.getText().toString();
            String description = binding.appointmentDescriptionText.getText().toString();
            String location = binding.appointmentLocationText.getText().toString();
            String notes = binding.appointmentNotesText.getText().toString();
            String appointmentStartDate = binding.appointmentStartDateText.getText().toString();
            String appointmentEndDate = binding.appointmentEndDateText.getText().toString();
            java.util.Date startDate = new java.util.Date();
            java.util.Date endDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            try {
                startDate = sdf.parse(appointmentStartDate);
                endDate = sdf.parse(appointmentEndDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Appointment appointment = new Appointment(title, description, location, notes, startDate, endDate, userId);
            appointmentViewModel.insert(appointment);
            NavHostFragment.findNavController(AddAppointmentFragment.this).navigateUp();
        });
    }

    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            // Set the chosen date in the format "dd/MM/yyyy"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.US);
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay);
            String formattedDate = sdf.format(calendar.getTime());
            binding.appointmentStartDateText.setText(formattedDate);
        }, year, month, day);
        datePickerDialog.show();
        showTimePickerDialog(binding.appointmentStartDateText.getText().toString());
    }

    private void showTimePickerDialog(String dateWithoutTime) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            String amPm;
            if (hourOfDay >= 12) {
                amPm = "PM";
            } else {
                amPm = "AM";
            }
            String time = dateWithoutTime + " " + hourOfDay + ":" + minute + " " + amPm;
            binding.appointmentStartDateText.setText(time);
        }, currentHour, currentMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}