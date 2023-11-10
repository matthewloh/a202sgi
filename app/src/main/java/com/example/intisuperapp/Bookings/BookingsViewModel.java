package com.example.intisuperapp.Bookings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookingsViewModel extends AndroidViewModel {
    private final BookingsRepository mRepository;

    public BookingsViewModel(Application application) {
        super(application);
        mRepository = new BookingsRepository(application);
    }

    public void insert(Bookings bookings) {
        mRepository.insert(bookings);
    }

    public void update(Bookings bookings) {
        mRepository.update(bookings);
    }

    public void delete(Bookings bookings) {
        mRepository.delete(bookings);
    }

    public LiveData<Bookings> getBookingsById(int id) {
        return mRepository.getBookingsById(id);
    }

    public LiveData<List<Bookings>> getBookingsByDate(int authorId) {
        return mRepository.getBookingsByDate(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByDateAsc(int authorId) {
        return mRepository.getBookingsByDateAsc(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByStartTime(int authorId) {
        return mRepository.getBookingsByStartTime(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByStartTimeAsc(int authorId) {
        return mRepository.getBookingsByStartTimeAsc(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByVenue(int authorId) {
        return mRepository.getBookingsByVenue(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByVenueAsc(int authorId) {
        return mRepository.getBookingsByVenueAsc(authorId);
    }

    public LiveData<List<Bookings>> getAllBookingsForUser(int authorId) {
        return mRepository.getAllBookingsForUser(authorId);
    }




}
