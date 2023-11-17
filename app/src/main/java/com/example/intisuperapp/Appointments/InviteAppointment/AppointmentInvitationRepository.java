package com.example.intisuperapp.Appointments.InviteAppointment;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

public class AppointmentInvitationRepository {
    private AppointmentInvitationDao mAppointmentInvitationDao;

    public AppointmentInvitationRepository(Application application) {
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mAppointmentInvitationDao = database.appointmentInvitationDao();
    }

    public void insert(AppointmentInvitation appointmentInvitation) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.insert(appointmentInvitation));
    }

    public void update(AppointmentInvitation appointmentInvitation) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.update(appointmentInvitation));
    }

    public void updateInvitationStatus(int invitationId, String status) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.updateInvitationStatus(invitationId, status));
    }

    public void delete(AppointmentInvitation appointmentInvitation) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.delete(appointmentInvitation));
    }


    public LiveData<AppointmentInvitation> getAppointmentInvitationByAppointmentIdAndUserId(int appointmentId, int userId) {
        return mAppointmentInvitationDao.getAppointmentInvitationByAppointmentIdAndUserId(appointmentId, userId);
    }

    public void deleteByAppointmentIdAndUserId(int appointmentId, int userId) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.deleteByAppointmentIdAndUserId(appointmentId, userId));
    }
}
