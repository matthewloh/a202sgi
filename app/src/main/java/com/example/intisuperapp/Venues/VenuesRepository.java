package com.example.intisuperapp.Venues;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;

public class VenuesRepository {

    private VenuesDao mVenuesDao;

    public VenuesRepository(Application application){
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mVenuesDao = database.venuesDao();
    }

    public LiveData<List<Venues>> getAllVenues(){
        return mVenuesDao.getAllVenues();
    }

    public void insert(Venues venues){
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.insert(venues));
    }

    public void update(Venues venues){
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.update(venues));
    }

    public void delete(Venues venues){
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.delete(venues));
    }

    public void deleteAllVenues(){
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.deleteAllVenues());
    }

    public LiveData<List<Venues>> getVenuesById(int venueId){
        return mVenuesDao.getVenuesById(venueId);
    }

    public LiveData<List<Venues>> getVenuesByName(String venueName){
        return mVenuesDao.getVenuesByName(venueName);
    }

    public LiveData<Integer> getVenuesCount(){
        return mVenuesDao.getVenuesCount();
    }

    public void deleteVenuesById(int venueId){
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.deleteVenuesById(venueId));
    }


    public void deleteVenuesByName(String venueName) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mVenuesDao.deleteVenuesByName(venueName));
    }
}
