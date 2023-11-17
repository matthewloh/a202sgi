package com.example.intisuperapp.Appointments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.intisuperapp.DBUtils.TimeConstants;
import com.example.intisuperapp.Firebase.viewmodel.FirebaseViewModel;
import com.example.intisuperapp.Firebase.viewmodel.PhotoViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentAddAppointmentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAppointmentFragment extends Fragment {


    private FragmentAddAppointmentBinding binding;

    private AppointmentViewModel appointmentViewModel;

    private UserSharedViewModel userSharedViewModel;

    private PhotoViewModel photoViewModel;

    private FirebaseViewModel firebaseViewModel;
    public int userId;

    private Uri mImageUri = null;

    String finalStartTime;
    String finalEndTime;

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
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        photoViewModel = new ViewModelProvider(requireActivity()).get(PhotoViewModel.class);
        firebaseViewModel = new ViewModelProvider(requireActivity()).get(FirebaseViewModel.class);
        userId = userSharedViewModel.getUserValue().getId();
        // Setting Home As Up Indicator in Fragment
        binding.appointmentStartDateText.setOnClickListener(view1 -> showDatePickerDialog(binding.appointmentStartDateText));
        binding.appointmentEndDateText.setOnClickListener(view1 -> showDatePickerDialog(binding.appointmentEndDateText));
        binding.appointmentStartTimeText.setOnClickListener(view1 -> showTimePickerDialog(binding.appointmentStartTimeText, "start"));
        binding.appointmentEndTimeText.setOnClickListener(view1 -> showTimePickerDialog(binding.appointmentEndTimeText, "end"));
        binding.appointmentImageView.setOnClickListener(v -> pickImagesFromGallery.launch("image/*"));
        binding.appointmentUploadImageButton.setOnClickListener(v -> pickImagesFromGallery.launch("image/*"));
        binding.appointmentCreateButton.setOnClickListener(view1 -> {
            binding.indeterminateBar.setVisibility(View.VISIBLE);
            String title = binding.appointmentTitleText.getText().toString();
            String description = binding.appointmentDescriptionText.getText().toString();
            String location = binding.appointmentLocationText.getText().toString();
            String notes = binding.appointmentNotesText.getText().toString();
            String appointmentStartDate = binding.appointmentStartDateText.getText().toString();
            String appointmentEndDate = binding.appointmentEndDateText.getText().toString();
            Date startDate = new Date();
            Date endDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
//            java.text.ParseException: Unparseable date: " 12:00 PM"
            try {
                startDate = sdf.parse(appointmentStartDate + " " + finalStartTime.replace(" ", "").replace("AM", "").replace("PM", ""));
                endDate = sdf.parse(appointmentEndDate + " " + finalEndTime.replace(" ", "").replace("AM", "").replace("PM", ""));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireActivity(), "Error parsing date", Toast.LENGTH_SHORT).show();
            }
            Appointment appointment = new Appointment(title, description, location, notes, startDate, endDate, "pending", "null", userId);
            firebaseViewModel.uploadImagesToAppointment(mImageUri, appointmentViewModel, appointment);
            firebaseViewModel.getTaskMutableLiveData().observe(getViewLifecycleOwner(), documentReferenceTask -> {
                if (documentReferenceTask.isSuccessful()) {
                    // Get the photo_table id from Android Room
                    binding.appointmentImageView.setImageResource(R.drawable.baseline_upload_24);
                    Toast.makeText(requireActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), "Image upload failed" + documentReferenceTask.getException().toString(), Toast.LENGTH_SHORT).show();
                }
                binding.indeterminateBar.setVisibility(View.GONE);
                NavHostFragment.findNavController(AddAppointmentFragment.this).navigateUp();
            });
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

    private void showTimePickerDialog(EditText timeEditText, String typeOfTime) {

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            String amPm;
            int displayHour = hourOfDay;
            if (hourOfDay == 12) {
                amPm = "PM";
            } else if (hourOfDay == 0) {
                amPm = "AM";
                hourOfDay = 12;
            } else if (hourOfDay > 12) {
                amPm = "PM";
                displayHour = hourOfDay - 12;
            } else {
                amPm = "AM";

            }
            Locale locale = new Locale("en", "MY");
            SimpleDateFormat sdf = new SimpleDateFormat(TimeConstants.TIME_24HRS_FORMAT, locale);
            Calendar savedTime = Calendar.getInstance();
            savedTime.set(0, 0, 0, hourOfDay, minute);
            Calendar displayCalendarTime = Calendar.getInstance();
            displayCalendarTime.set(0, 0, 0, displayHour, minute);
            if (typeOfTime.equals("start")) {
                finalStartTime = sdf.format(savedTime.getTime());
            } else {
                finalEndTime = sdf.format(savedTime.getTime());
            }
            String formattedTime = sdf.format(displayCalendarTime.getTime()) + " " + amPm;
            timeEditText.setText(formattedTime);
        }, currentHour, currentMinute, true);
        timePickerDialog.show();
    }

    ActivityResultLauncher<String> pickImagesFromGallery = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    // Handle the returned Uri
                    if (result != null) {
                        mImageUri = result;
                        binding.appointmentImageView.setImageURI(mImageUri);
                    }
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}