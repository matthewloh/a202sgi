package com.example.intisuperapp.Appointments;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitation;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitationDao;
import com.example.intisuperapp.INTISuperappDatabase;
import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.LoginAndRegistration.UserDao;

import java.util.List;

public class AppointmentRepository {

    private AppointmentDao mAppointmentDao;

    private UserDao mUserDao;

    private AppointmentInvitationDao mAppointmentInvitationDao;
    private LiveData<List<Appointment>> mAllAppointments;

    public AppointmentRepository(Application application) {
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mUserDao = database.userDao();
        mAppointmentDao = database.appointmentDao();
        mAppointmentInvitationDao = database.appointmentInvitationDao();
    }

    public LiveData<List<Appointment>> getAllAppointmentsForUser(int authorId) {
        return mAppointmentDao.getAllAppointmentsForUser(authorId);
    }

    public void insert(Appointment appointment) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentDao.insert(appointment));
    }

    public void update(Appointment appointment) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentDao.update(appointment));
    }

    public void updateAppointmentStatus(int id, String status) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentDao.updateAppointmentStatus(id, status));
    }

    public void delete(Appointment appointment) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentDao.delete(appointment));
    }

    public void deleteAppointmentById(int id) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentDao.deleteAppointmentById(id));
    }

    public LiveData<Appointment> getAppointmentById(int id) {
        return mAppointmentDao.getAppointmentById(id);
    }

    public LiveData<List<Appointment>> getAppointmentsByDate(int authorId) {
        return mAppointmentDao.getAppointmentsByDate(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByDateAsc(int authorId) {
        return mAppointmentDao.getAppointmentsByDateAsc(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByTitle(int authorId) {
        return mAppointmentDao.getAppointmentsByTitle(authorId);
    }

    public LiveData<List<Appointment>> getAppointmentsByTitleAsc(int authorId) {
        return mAppointmentDao.getAppointmentsByTitleAsc(authorId);
    }

    public void insertInvitation(AppointmentInvitation appointmentInvitation) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mAppointmentInvitationDao.insert(appointmentInvitation));
    }

    public LiveData<List<AppointmentInvitation>> getAppointmentInvitationByAppointmentId(int appointmentId) {
        return mAppointmentInvitationDao.getAppointmentInvitationByAppointmentId(appointmentId);
    }

    public LiveData<List<AppointmentInvitation>> getAppointmentInvitationByInviteeId(int inviteeId) {
        return mAppointmentInvitationDao.getAppointmentInvitationByInviteeId(inviteeId);
    }

    public LiveData<List<AppointmentInvitation>> getPendingAppointmentInvitationByInviteeId(int inviteeId) {
        return mAppointmentInvitationDao.getPendingAppointmentInvitationByInviteeId(inviteeId);
    }

    public LiveData<AppointmentInvitation> getAppointmentInvitationByAppointmentIdAndUserId(int appointmentId, int userId) {
        return mAppointmentInvitationDao.getAppointmentInvitationByAppointmentIdAndUserId(appointmentId, userId);
    }
    public LiveData<List<User>> getAllStudents() {
        return mUserDao.getAllStudents();
    }

    public LiveData<List<User>> getAllLecturers() {
        return mUserDao.getAllLecturers();
    }
}
