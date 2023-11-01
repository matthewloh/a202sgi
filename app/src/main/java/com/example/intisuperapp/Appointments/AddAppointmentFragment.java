package com.example.intisuperapp.Appointments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.MainActivity;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentAddAppointmentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        ((MainActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Setting Home As Up Indicator in Fragment
        ((MainActivity) requireActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        binding.appointmentStartDateText.setOnClickListener(view1 -> showDatePickerDialog(
                binding.appointmentStartDateText
        ));
        binding.appointmentEndDateText.setOnClickListener(view1 -> showDatePickerDialog(
                binding.appointmentEndDateText
        ));
        binding.appointmentStartTimeText.setOnClickListener(
                view1 -> showTimePickerDialog(binding.appointmentStartTimeText)
        );
        binding.appointmentEndTimeText.setOnClickListener(
                view1 -> showTimePickerDialog(binding.appointmentEndTimeText)
        );
        binding.appointmentCreateButton.setOnClickListener(view1 -> {
            int userId = AddAppointmentFragmentArgs.fromBundle(getArguments()).getUserId();
            String title = binding.appointmentsTitle.getText().toString();
            String description = binding.appointmentDescriptionText.getText().toString();
            String location = binding.appointmentLocationText.getText().toString();
            String notes = binding.appointmentNotesText.getText().toString();
            String appointmentStartDate = binding.appointmentStartDateText.getText().toString();
            String appointmentEndDate = binding.appointmentEndDateText.getText().toString();
            String appointmentStartTime = binding.appointmentStartTimeText.getText().toString();
            String appointmentEndTime = binding.appointmentEndTimeText.getText().toString();
            java.util.Date startDate = new java.util.Date();
            java.util.Date endDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            try {
                startDate = sdf.parse(appointmentStartDate + " " + appointmentStartTime);
                endDate = sdf.parse(appointmentEndDate + " " + appointmentEndTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Appointment appointment = new Appointment(title, description, location, notes, startDate, endDate, userId);
            appointmentViewModel.insert(appointment);
            NavHostFragment.findNavController(AddAppointmentFragment.this).navigateUp();
        });
    }

    private void showDatePickerDialog(EditText dateEditText) {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            // Set the chosen date in the format "dd/MM/yyyy" with Malaysian locale
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("ms", "MY"));
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay);
            String formattedDate = sdf.format(calendar.getTime());
            dateEditText.setText(formattedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(EditText timeEditText) {

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            String amPm;
            if (hourOfDay >= 12) {
                amPm = "PM";
                hourOfDay -= 12;
            } else {
                amPm = "AM";
            }
            // Set to Malaysia's time zone
            Locale locale = new Locale("en", "MY");
            String formattedTime = String.format(locale, "%02d:%02d %s", hourOfDay, minute, amPm);
            timeEditText.setText(formattedTime);
        }, currentHour, currentMinute, true);
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}