package com.example.intisuperapp.Venues;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VenuesViewModel extends AndroidViewModel {

    private final VenuesRepository mRepository;

    public VenuesViewModel(Application application) {
        super(application);
        mRepository = new VenuesRepository(application);
    }

    public void insert(Venues venues) {
        mRepository.insert(venues);
    }

    public void update(Venues venues) {
        mRepository.update(venues);
    }

    public void delete(Venues venues) {
        mRepository.delete(venues);
    }

    public void deleteAllVenues() {
        mRepository.deleteAllVenues();
    }

    public void deleteVenuesById(int venueId) {
        mRepository.deleteVenuesById(venueId);
    }

    public LiveData<List<Venues>> getAllVenues() {
        return mRepository.getAllVenues();
    }

    public LiveData<List<Venues>> getVenuesById(int venueId) {
        return mRepository.getVenuesById(venueId);
    }

    public LiveData<List<Venues>> getVenuesByName(String venueName) {
        return mRepository.getVenuesByName(venueName);
    }

    public LiveData<Integer> getVenuesCount() {
        return mRepository.getVenuesCount();
    }


    public void deleteVenuesByName(String venueName) {
        mRepository.deleteVenuesByName(venueName);
    }
}
