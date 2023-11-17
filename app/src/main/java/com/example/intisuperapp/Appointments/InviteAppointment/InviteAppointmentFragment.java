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
import androidx.navigation.fragment.NavHostFragment;
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
    private LiveData<List<AppointmentInvitation>> invites;


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
            binding.invitesAppointmentsTitle.setText("All Invites for " + user.getFullname().split(" ")[0]);
            invites = appointmentViewModel.getAppointmentInvitationByInviteeId(userId);
            loadInvitesIntoRecyclerView(invites);
            binding.invitesBack.setOnClickListener(v -> {
                // Return to the main appointments fragment
                NavHostFragment.findNavController(InviteAppointmentFragment.this)
                        .navigateUp();
            });
            binding.viewAccepted.setOnClickListener(v -> {
                binding.invitesAppointmentsTitle.setText("Accepted invites for " + user.getFullname().split(" ")[0]);
                invites = appointmentViewModel.getAppointmentInvitationByInviteeIdAndStatus(userId, "accepted");
                loadInvitesIntoRecyclerView(invites);
            });
            binding.viewDeclined.setOnClickListener(v -> {
                binding.invitesAppointmentsTitle.setText("Declined invites for " + user.getFullname().split(" ")[0]);
                invites = appointmentViewModel.getAppointmentInvitationByInviteeIdAndStatus(userId, "declined");
                loadInvitesIntoRecyclerView(invites);
            });
            binding.invitesViewPending.setOnClickListener(v -> {
                binding.invitesAppointmentsTitle.setText("Pending invites for " + user.getFullname().split(" ")[0]);
                invites = appointmentViewModel.getAppointmentInvitationByInviteeIdAndStatus(userId, "pending");
                loadInvitesIntoRecyclerView(invites);
            });
            binding.invitesViewAll.setOnClickListener(v -> {
                binding.invitesAppointmentsTitle.setText("All Invites for " + user.getFullname().split(" ")[0]);
                invites = appointmentViewModel.getAppointmentInvitationByInviteeId(userId);
                loadInvitesIntoRecyclerView(invites);
            });
        });
    }

    private void loadInvitesIntoRecyclerView(LiveData<List<AppointmentInvitation>> invites) {
        invites.observe(getViewLifecycleOwner(), appointmentInvitations -> {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}