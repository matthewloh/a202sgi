package com.example.intisuperapp.Appointments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitation;
import com.example.intisuperapp.LoginAndRegistration.User;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {
    private final AppointmentRepository mRepository;

    public AppointmentViewModel(Application application) {
        super(application);
        mRepository = new AppointmentRepository(application);
    }

    public void insert(Appointment appointment) {
        mRepository.insert(appointment);
    }

    public void update(Appointment appointment) {
        mRepository.update(appointment);
    }

    public void updateAppointmentStatus(int id, String status) {
        mRepository.updateAppointmentStatus(id, status);
    }

    public void delete(Appointment appointment) {
        mRepository.delete(appointment);
    }

    public LiveData<Appointment> getAppointmentById(int id) {
        return mRepository.getAppointmentById(id);
    }

    public LiveData<List<Appointment>> getAppointmentsByDate(int authorId) {
        return mRepository.getAppointmentsByDate(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByDateAsc(int authorId) {
        return mRepository.getAppointmentsByDateAsc(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByTitle(int authorId) {
        return mRepository.getAppointmentsByTitle(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByTitleAsc(int authorId) {
        return mRepository.getAppointmentsByTitleAsc(authorId);
    }

    public void deleteAppointmentById(int id) {
        mRepository.deleteAppointmentById(id);
    }

    public LiveData<List<Appointment>> getAllAppointmentsForUser(int authorId) {
        return mRepository.getAllAppointmentsForUser(authorId);
    }

    public LiveData<List<AppointmentInvitation>> getAppointmentInvitationByAppointmentId(int appointment_id) {
        return mRepository.getAppointmentInvitationByAppointmentId(appointment_id);
    }

    public LiveData<List<AppointmentInvitation>> getAppointmentInvitationByInviteeId(int invitee_id) {
        return mRepository.getAppointmentInvitationByInviteeId(invitee_id);
    }

    // Get all students
    public LiveData<List<User>> getAllStudents() {
        return mRepository.getAllStudents();
    }

    // Get all lecturers
    public LiveData<List<User>> getAllLecturers() {
        return mRepository.getAllLecturers();
    }

}
