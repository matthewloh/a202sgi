package com.example.intisuperapp.Appointments.InviteAppointment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppointmentInvitationViewModel extends AndroidViewModel {
    private final AppointmentInvitationRepository mRepository;

    public AppointmentInvitationViewModel(Application application) {
        super(application);
        mRepository = new AppointmentInvitationRepository(application);
    }

    public void insert(AppointmentInvitation appointmentInvitation) {
        mRepository.insert(appointmentInvitation);
    }

    public void update(AppointmentInvitation appointmentInvitation) {
        mRepository.update(appointmentInvitation);
    }

    public void updateInvitationStatus(int invitationId, String status) {
        mRepository.updateInvitationStatus(invitationId, status);
    }

    public void delete(AppointmentInvitation appointmentInvitation) {
        mRepository.delete(appointmentInvitation);
    }

    public LiveData<AppointmentInvitation> getAppointmentInvitationByAppointmentIdAndUserId(int appointmentId, int userId) {
        return mRepository.getAppointmentInvitationByAppointmentIdAndUserId(appointmentId, userId);
    }

    public void deleteByAppointmentIdAndUserId(int appointmentId, int userId) {
        mRepository.deleteByAppointmentIdAndUserId(appointmentId, userId);
    }
}
