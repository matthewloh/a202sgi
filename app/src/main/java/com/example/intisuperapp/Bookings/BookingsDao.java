package com.example.intisuperapp.Bookings;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface BookingsDao {
    @Insert
    void insert(Bookings bookings);

    @Update
    void update(Bookings bookings);

    @Delete
    void delete(Bookings bookings);

    @Query("DELETE FROM Bookings")
    void deleteAllBookings();

    // Delete Bookings
    @Query("DELETE FROM Bookings WHERE id = :id")
    void deleteBookingsById(int id);

    // Get all bookings for a user
    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER by id ASC")
    LiveData<List<Bookings>> getAllBookingsForUser(int id);

    //    // Retrieve bookings details by id
    @Query("SELECT * FROM Bookings WHERE authorId = :id")
    LiveData<Bookings> getBookingsById(int id);

    //
    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY date DESC")
    LiveData<List<Bookings>>  getBookingsByDate(int id);

    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY date ASC")
    LiveData<List<Bookings>> getBookingsByDateAsc(int id);

    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY startTime DESC")
    LiveData<List<Bookings>> getBookingsByStartTime(int id);

    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY startTime ASC")
    LiveData<List<Bookings>> getBookingsByStartTimeAsc(int id);


    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY venue DESC")
    LiveData<List<Bookings>> getBookingsByVenue(int id);

    @Query("SELECT * FROM Bookings WHERE authorId = :id ORDER BY venue ASC")
    LiveData<List<Bookings>> getBookingsByVenueAsc(int id);

}


