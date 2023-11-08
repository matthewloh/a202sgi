package com.example.intisuperapp.Appointments;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;

public class AppointmentRepository {

    private AppointmentDao mAppointmentDao;

    private LiveData<List<Appointment>> mAllAppointments;

    public AppointmentRepository(Application application) {
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mAppointmentDao = database.appointmentDao();
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

}
