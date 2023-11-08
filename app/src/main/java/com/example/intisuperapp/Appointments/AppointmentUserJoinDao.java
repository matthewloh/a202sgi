package com.example.intisuperapp.Appointments;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.intisuperapp.LoginAndRegistration.User;

@Dao
public interface AppointmentUserJoinDao {
    @Insert
    void insert(AppointmentUserJoin appointmentUserJoin);

    @Query("SELECT * FROM Appointment INNER JOIN appointment_user_join ON Appointment.id = appointment_user_join.appointmentId WHERE appointment_user_join.userId = :userId")
    Appointment[] getAppointmentsForUser(final int userId);

    @Query("SELECT * FROM User INNER JOIN appointment_user_join ON User.id = appointment_user_join.userId WHERE appointment_user_join.appointmentId = :appointmentId")
    User[] getUsersForAppointment(final int appointmentId);
}
