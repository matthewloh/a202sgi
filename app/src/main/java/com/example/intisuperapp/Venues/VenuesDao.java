package com.example.intisuperapp.Venues;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VenuesDao {

    @Insert
    void insert(Venues venues);

    @Update
    void update(Venues venues);

    @Delete
    void delete(Venues venues);

    @Query("DELETE FROM Venues")
    void deleteAllVenues();

    // Delete Venues by id
    @Query("DELETE FROM Venues WHERE venueId = :venueId")
    void deleteVenuesById(int venueId);

    // Get all Venues
    @Query("SELECT * FROM Venues ORDER by venueId ASC")
    LiveData<List<Venues>> getAllVenues();

    // Retrieve Venues details by id
    @Query("SELECT * FROM Venues WHERE venueId = :venueId")
    LiveData<List<Venues>> getVenuesById(int venueId);

    // Retrieve Venues  and sort them by name
    @Query("SELECT * FROM Venues WHERE venueName = :venueName")
    LiveData<List<Venues>> getVenuesByName(String venueName);


}
