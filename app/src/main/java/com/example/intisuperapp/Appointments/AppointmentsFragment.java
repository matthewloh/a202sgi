package com.example.intisuperapp.Appointments;

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

import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.LoginAndRegistration.UserSharedViewModel;
import com.example.intisuperapp.R;
import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.util.List;

public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    LiveData<List<Appointment>> appointments;

    private AppointmentViewModel appointmentViewModel;

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
        userSharedViewModel = new ViewModelProvider(getActivity()).get(UserSharedViewModel.class);
        userSharedViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.appointmentsTitle.setText("Appointments for " + user.getFullname());
            userId = user.getId();
            appointments = appointmentViewModel.getAllAppointmentsForUser(userId);
            appointments.observe(getViewLifecycleOwner(), retrieved -> {
                AppointmentAdapter adapter = new AppointmentAdapter(retrieved);
                adapter.setOnItemClickListener(appointment -> {
                    Toast.makeText(getActivity(), "Appointment Clicked", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Appointment Title" + appointment.getTitle(), Toast.LENGTH_SHORT).show();
                    // Navigate to the EditAppointmentFragment
//                                            AppointmentsFragmentDirections.ActionAppointmentsFragmentToEditAppointmentFragment action =
//                                                    AppointmentsFragmentDirections.actionAppointmentsFragmentToEditAppointmentFragment(appointment.getId(), userId);
//                                            NavHostFragment.findNavController(AppointmentsFragment.this).navigate(action);
                });
                adapter.setOnItemLongClickListener(appointment -> {
                    Toast.makeText(getActivity(), "Appointment Long Clicked", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Appointment Image Url" + appointment.getImageUrl(), Toast.LENGTH_SHORT).show();
                });
                binding.appointmentsRecyclerView.setAdapter(adapter);
                binding.appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            });
        });
        binding.addAppointmentFab.setOnClickListener(v -> {
            // Navigate to the AddAppointmentFragment
            NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_appointmentsFragment_to_addAppointmentsFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}