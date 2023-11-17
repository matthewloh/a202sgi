package com.example.intisuperapp.Appointments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitationViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.util.List;

public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    LiveData<List<Appointment>> appointments;

    private AppointmentViewModel appointmentViewModel;

    private AppointmentInvitationViewModel appointmentInvitationViewModel;
    private UserSharedViewModel userSharedViewModel;

    public int userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Observe the User LiveData to get the user's ID
        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        appointmentInvitationViewModel = new ViewModelProvider(requireActivity()).get(AppointmentInvitationViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.appointmentsTitle.setText("All Appointments for " + user.getFullname().split(" ")[0]);
            userId = user.getId();
            appointments = appointmentViewModel.getAllAppointmentsForUser(userId);
            loadAppsIntoRecyclerView(appointments);
            binding.viewCompleted.setOnClickListener(
                    v -> {
                        binding.appointmentsTitle.setText("Completed appointments for " + user.getFullname().split(" ")[0]);
                        appointments = appointmentViewModel.getAllCompletedAppointmentsForUser(userId);
                        loadAppsIntoRecyclerView(appointments);
                    }
            );
            binding.viewPending.setOnClickListener(
                    v -> {
                        binding.appointmentsTitle.setText("Pending appointments for " + user.getFullname().split(" ")[0]);
                        appointments = appointmentViewModel.getAllPendingAppointmentsForUser(userId);
                        loadAppsIntoRecyclerView(appointments);
                    }
            );
            binding.viewCancelled.setOnClickListener(
                    v -> {
                        binding.appointmentsTitle.setText("Cancelled appointments for " + user.getFullname().split(" ")[0]);
                        appointments = appointmentViewModel.getAllCancelledAppointmentsForUser(userId);
                        loadAppsIntoRecyclerView(appointments);
                    }
            );
            binding.viewAll.setOnClickListener(
                    v -> {
                        binding.appointmentsTitle.setText("All Appointments for " + user.getFullname().split(" ")[0]);
                        appointments = appointmentViewModel.getAllAppointmentsForUser(userId);
                        loadAppsIntoRecyclerView(appointments);
                    }
            );
        });
        binding.inviteButton.setOnClickListener(v -> {
            // Navigate to the InviteFragment
            NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_appointmentsFragment_to_inviteAppointment);
        });
        binding.addAppointmentFab.setOnClickListener(v -> {
            // Navigate to the AddAppointmentFragment
            NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_appointmentsFragment_to_addAppointmentsFragment);
        });
    }

    private void loadAppsIntoRecyclerView(LiveData<List<Appointment>> appointments) {
        appointments.observe(getViewLifecycleOwner(), retrieved -> {
            if (retrieved == null) {
                Toast.makeText(getActivity(), "No appointments found.", Toast.LENGTH_SHORT).show();
                return;
            }
            AppointmentAdapter adapter = new AppointmentAdapter(retrieved);
            adapter.setOnItemClickListener(appointment -> {
                // View or Edit Appointment
                AppointmentsFragmentDirections.ActionAppointmentsFragmentToViewAppointmentFragment action = AppointmentsFragmentDirections.actionAppointmentsFragmentToViewAppointmentFragment(appointment.getId());
                NavHostFragment.findNavController(AppointmentsFragment.this).navigate(action);
            });
            adapter.setOnItemLongClickListener(appointment -> {
                // Delete appointment with alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to delete this appointment?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    appointmentViewModel.delete(appointment);
                    Toast.makeText(getActivity(), "Appointment deleted", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                });
                builder.create().show();
            });
            binding.appointmentsRecyclerView.setAdapter(adapter);
            binding.appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}