package com.example.intisuperapp.Appointments.InviteAppointment;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.Appointments.AppointmentViewModel;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.databinding.FragmentInviteAppointmentBinding;

import java.util.List;

public class InviteAppointmentFragment extends Fragment {

    private FragmentInviteAppointmentBinding binding;


    private AppointmentViewModel appointmentViewModel;

    private AppointmentInvitationViewModel appointmentInvitationViewModel;
    private UserSharedViewModel userSharedViewModel;

    public int userId;
    private LiveData<List<AppointmentInvitation>> appointments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInviteAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Observe the User LiveData to get the user's ID
        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        appointmentInvitationViewModel = new ViewModelProvider(requireActivity()).get(AppointmentInvitationViewModel.class);
        userSharedViewModel = new ViewModelProvider(requireActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            userId = user.getId();
            appointmentViewModel.getAppointmentInvitationByInviteeId(userId).observe(getViewLifecycleOwner(), appointmentInvitations -> {
                if (appointmentInvitations != null) {
                    InviteAppointmentAdapter adapter = new InviteAppointmentAdapter(appointmentInvitations, appointmentInvitationViewModel);
                    adapter.setOnItemClickListener(appointment -> {
                    });
                    adapter.setOnItemLongClickListener(appointment -> {
                    });
                    binding.appointmentsRecyclerView.setAdapter(adapter);
                    binding.appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    Toast.makeText(getActivity(), "Appointments Not Retrieved", Toast.LENGTH_SHORT).show();
                }
            });
//            if (appointments.getValue() != null) {
//                Toast.makeText(getActivity(), "Appointments Retrieved", Toast.LENGTH_SHORT).show();
//                appointments.observe(getViewLifecycleOwner(), retrieved -> {
//                    InviteAppointmentAdapter adapter = new InviteAppointmentAdapter(retrieved, appointmentInvitationViewModel);
//                    adapter.setOnItemClickListener(appointment -> {
//                        Toast.makeText(getActivity(), "Appointment Clicked", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "Appointment Image Url" + appointment.getImageUrl(), Toast.LENGTH_SHORT).show();
//                    });
//                    adapter.setOnItemLongClickListener(appointment -> {
//                        Toast.makeText(getActivity(), "Appointment Long Clicked", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "Appointment Image Url" + appointment.getImageUrl(), Toast.LENGTH_SHORT).show();
//                    });
//                });
//            } else {
//                Toast.makeText(getActivity(), "Appointments Not Retrieved", Toast.LENGTH_SHORT).show();
//            }
        });
        binding.viewAll.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "View All Clicked", Toast.LENGTH_SHORT).show();
        });
        binding.viewAccepted.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "View Accepted Clicked", Toast.LENGTH_SHORT).show();
        });
        binding.viewDeclined.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "View Declined Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}