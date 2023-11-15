package com.example.intisuperapp.Appointments.ViewAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.Appointments.AppointmentViewModel;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitationViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.databinding.FragmentViewAppointmentBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewAppointmentFragment extends Fragment {

    private FragmentViewAppointmentBinding binding;

    private AppointmentViewModel appointmentViewModel;

    private AppointmentInvitationViewModel appointmentInvitationViewModel;

    private UserSharedViewModel userSharedViewModel;

    private SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private InviteUsersAdapter adapter;
    private SimpleDateFormat targetTimeFormat = new SimpleDateFormat("HH:mma", Locale.getDefault());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewAppointmentFragmentArgs args = ViewAppointmentFragmentArgs.fromBundle(getArguments());
        int appointmentId = args.getAppointmentId();
        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        appointmentInvitationViewModel = new ViewModelProvider(requireActivity()).get(AppointmentInvitationViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            appointmentViewModel.getAppointmentById(appointmentId).observe(getViewLifecycleOwner(), appointment -> {
                if (appointment != null) {
                    binding.appointmentTitle.setText(appointment.getTitle());
                    binding.appointmentDesc.setText(appointment.getDescription());
                    Date tempStartDate = appointment.getStartDate();
                    Date tempEndDate = appointment.getEndDate();
                    Date tempStartTime = appointment.getStartDate();
                    Date tempEndTime = appointment.getEndDate();
                    try {
                        tempStartDate = originalDateFormat.parse(appointment.getStartDate().toString());
                        tempEndDate = originalDateFormat.parse(appointment.getEndDate().toString());
                        tempStartTime = originalDateFormat.parse(appointment.getStartDate().toString());
                        tempEndTime = originalDateFormat.parse(appointment.getEndDate().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tempStartDate != null && tempEndDate != null && tempStartTime != null && tempEndTime != null) {
                        String formattedStartDate = targetDateFormat.format(tempStartDate);
                        String formattedEndDate = targetDateFormat.format(tempEndDate);
                        String formattedStartTime = targetTimeFormat.format(tempStartTime);
                        String formattedEndTime = targetTimeFormat.format(tempEndTime);
                        binding.appointmentStartDate.setText(formattedStartDate);
                        binding.appointmentEndDate.setText(formattedEndDate);
                        binding.appointmentStartTime.setText(formattedStartTime);
                        binding.appointmentEndTime.setText(formattedEndTime);
                    }
                    binding.appointmentLocation.setText(appointment.getLocation());
                    binding.appointmentNotes.setText(appointment.getNotes());
                    binding.appointmentStatus.setText("Status: " + appointment.getApptStatus());
                    if (appointment.getApptStatus().equals("pending")) {
                        binding.completeAppointmentButton.setVisibility(View.VISIBLE);
                        binding.cancelAppointmentButton.setVisibility(View.VISIBLE);
                        binding.resetAppointmentStatusButton.setVisibility(View.GONE);
                    } else if (appointment.getApptStatus().equals("completed")) {
                        binding.completeAppointmentButton.setVisibility(View.GONE);
                        binding.cancelAppointmentButton.setVisibility(View.GONE);
                        binding.resetAppointmentStatusButton.setVisibility(View.VISIBLE);
                    } else if (appointment.getApptStatus().equals("cancelled")) {
                        binding.completeAppointmentButton.setVisibility(View.GONE);
                        binding.cancelAppointmentButton.setVisibility(View.GONE);
                        binding.resetAppointmentStatusButton.setVisibility(View.VISIBLE);
                    }
                    binding.completeAppointmentButton.setOnClickListener(v -> {
                        appointment.setApptStatus("completed");
                        appointmentViewModel.update(appointment);
                        Toast.makeText(requireContext(), "Appointment Completed", Toast.LENGTH_SHORT).show();
                    });
                    binding.cancelAppointmentButton.setOnClickListener(v -> {
                        appointment.setApptStatus("cancelled");
                        appointmentViewModel.update(appointment);
                        Toast.makeText(requireContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show();
                    });
                    binding.resetAppointmentStatusButton.setOnClickListener(v -> {
                        appointment.setApptStatus("pending");
                        appointmentViewModel.update(appointment);
                        Toast.makeText(requireContext(), "Appointment Status Reset", Toast.LENGTH_SHORT).show();
                    });
                    if (user.getRole().equals("student")) {
                        appointmentViewModel.getAllLecturers().observe(getViewLifecycleOwner(), lecturer -> {
                            if (lecturer != null) {
                                adapter = new InviteUsersAdapter(lecturer, appointmentInvitationViewModel, appointment, user);
                                binding.inviteeRecyclerView.setAdapter(adapter);
                                binding.inviteeRecyclerView.setVisibility(View.VISIBLE);
                                binding.inviteeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            }
                        });
                    } else if (user.getRole().equals("lecturer")) {
                        appointmentViewModel.getAllStudents().observe(getViewLifecycleOwner(), student -> {
                            if (student != null) {
                                adapter = new InviteUsersAdapter(student, appointmentInvitationViewModel, appointment, user);
                                binding.inviteeRecyclerView.setAdapter(adapter);
                                binding.inviteeRecyclerView.setVisibility(View.VISIBLE);
                                binding.inviteeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            }
                        });
                    }
                } else {
                    Toast.makeText(requireContext(), "Appointment not found", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}