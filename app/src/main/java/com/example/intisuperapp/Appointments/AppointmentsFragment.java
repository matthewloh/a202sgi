package com.example.intisuperapp.Appointments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.util.List;

public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    LiveData<List<Appointment>> appointments;

    private AppointmentViewModel appointmentViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int userId = AppointmentsFragmentArgs.fromBundle(getArguments()).getUserId();
        // Observe the User LiveData to get the user's ID
        appointmentViewModel = new ViewModelProvider(requireActivity(),
                new AppointmentViewModelFactory(requireActivity().getApplication(), userId))
                .get(AppointmentViewModel.class);

        // Now, you can use the appointmentViewModel to fetch appointments.
        appointments = appointmentViewModel.getAllAppointmentsForUser(userId);
        appointments.observe(getViewLifecycleOwner(), retrieved -> {
            AppointmentAdapter adapter = new AppointmentAdapter(retrieved);
            binding.appointmentsRecyclerView.setAdapter(adapter);
            binding.appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.addAppointmentFab.setOnClickListener(view1 -> {
                // Navigate to the AddAppointmentFragment
                AppointmentsFragmentDirections.ActionAppointmentsFragmentToAddAppointmentsFragment action =
                        AppointmentsFragmentDirections.actionAppointmentsFragmentToAddAppointmentsFragment(userId);
                NavHostFragment.findNavController(AppointmentsFragment.this).navigate(action);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}