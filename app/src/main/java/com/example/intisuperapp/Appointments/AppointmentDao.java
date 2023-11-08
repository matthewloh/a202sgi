package com.example.intisuperapp.Appointments;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.intisuperapp.LoginAndRegistration.User;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Insert
    void insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);

    @Query("DELETE FROM Appointment")
    void deleteAllAppointments();

    // Delete Appointment
    @Query("DELETE FROM Appointment WHERE id = :id")
    void deleteAppointmentById(int id);

    // Get all appointments for a user
    @Query("SELECT * FROM Appointment WHERE authorId = :authorId ORDER by id DESC")
    LiveData<List<Appointment>> getAllAppointmentsForUser(int authorId);

    // Retrieve appointment details by id
    @Query("SELECT * FROM Appointment WHERE id = :id")
    LiveData<Appointment> getAppointmentById(int id);

    // Retrieve appointment details and sort them by date
    @Query("SELECT * FROM Appointment WHERE authorId = :authorId ORDER by startDate DESC")
    LiveData<List<Appointment>> getAppointmentsByDate(int authorId);

    // Retrieve appointment details and sort them by date
    @Query("SELECT * FROM Appointment WHERE authorId = :authorId ORDER by startDate ASC")
    LiveData<List<Appointment>> getAppointmentsByDateAsc(int authorId);

    // Retrieve appointment details and sort them by title
    @Query("SELECT * FROM Appointment WHERE authorId = :authorId ORDER by title DESC")
    LiveData<List<Appointment>> getAppointmentsByTitle(int authorId);

    // Retrieve appointment details and sort them by title
    @Query("SELECT * FROM Appointment WHERE authorId = :authorId ORDER by title ASC")
    LiveData<List<Appointment>> getAppointmentsByTitleAsc(int authorId);
    //    public class AppointmentWithPhoto {
    //        @Embedded
    //        public Appointment appointment;
    //
    //        @ColumnInfo(name = "imageUrl")
    //        public String imageUrl;
    //    }
    // Querying appointments with their photos and authorId
    @Query("SELECT Appointment.*, " +
            "photo_table.imageUrl FROM Appointment INNER JOIN photo_table ON Appointment.imageId = photo_table.id WHERE Appointment.authorId = :authorId ORDER BY Appointment.id DESC")
    LiveData<List<AppointmentWithPhoto>> getAllAppointmentsWithPhoto(int authorId);
    @Query("SELECT Appointment.*, " +
            "photo_table.imageUrl FROM Appointment INNER JOIN photo_table ON Appointment.imageId = photo_table.id WHERE Appointment.id = :appointmentId")
    LiveData<AppointmentWithPhoto> getAppointmentWithPhotoById(int appointmentId);



}