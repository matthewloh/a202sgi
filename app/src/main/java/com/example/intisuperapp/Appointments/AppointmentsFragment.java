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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.LoginAndRegistration.UserDao;
import com.example.intisuperapp.LoginAndRegistration.UserViewModel;
import com.example.intisuperapp.databinding.FragmentAppointmentsBinding;

import java.util.List;

public class AppointmentsFragment extends Fragment {
    private FragmentAppointmentsBinding binding;

    LiveData<List<Appointment>> appointments;

    private AppointmentViewModel appointmentViewModel;

    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        // Observe the User LiveData to get the user's ID
        userViewModel.getUserByFullName("John Doe").observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                int johnId = user.getId();

                // Create the AppointmentViewModel using the user's ID
                appointmentViewModel = new ViewModelProvider(getActivity(),
                        new AppointmentViewModelFactory(getActivity().getApplication(), johnId))
                        .get(AppointmentViewModel.class);

                // Now, you can use the appointmentViewModel to fetch appointments.
                appointments = appointmentViewModel.getAllAppointmentsForUser(johnId);
                appointments.observe(getViewLifecycleOwner(), appointments1 -> {
                    AppointmentAdapter adapter = new AppointmentAdapter(appointments1);
                    binding.appointmentsRecyclerView.setAdapter(adapter);
                    binding.appointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}