package com.example.intisuperapp.Bookings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookingsViewModel extends AndroidViewModel {
    private final BookingsRepository mRepository;

    public BookingsViewModel(Application application, int authorId) {
        super(application);
        mRepository = new BookingsRepository(application, authorId);
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

    public LiveData<List<Bookings>> getBookingsByTitle(int authorId) {
        return mRepository.getBookingsByTitle(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByTitleAsc(int authorId) {
        return mRepository.getBookingsByTitleAsc(authorId);
    }

    public LiveData<List<Bookings>> getAllBookingsForUser(int authorId) {
        return mRepository.getAllBookingsForUser(authorId);
    }




}
