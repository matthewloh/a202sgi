package com.example.intisuperapp.Bookings;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;

public class BookingsRepository {
    private BookingsDao mBookingsDao;
    private LiveData<List<Bookings>> mAllBookings;

    public BookingsRepository(Application application, int authorId) {
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mBookingsDao = database.bookingsDao();
    }

    public LiveData<List<Bookings>> getAllBookingsForUser(int authorId) {
        return mBookingsDao.getAllBookingsForUser(authorId);
    }

    public void insert(Bookings bookings) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mBookingsDao.insert(bookings));
    }

    public void update(Bookings bookings) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mBookingsDao.update(bookings));
    }

    public void delete(Bookings bookings) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mBookingsDao.delete(bookings));
    }

    public void deleteBookingsById(int id) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mBookingsDao.deleteBookingsById(id));
    }

    public LiveData<Bookings> getBookingsById(int id) {
        return mBookingsDao.getBookingsById(id);
    }

    public LiveData<List<Bookings>> getBookingsByDate(int authorId) {
        return mBookingsDao.getBookingsByDate(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByDateAsc(int authorId) {
        return mBookingsDao.getBookingsByDateAsc(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByTitle(int authorId) {
        return mBookingsDao.getBookingsByTitle(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByTitleAsc(int authorId) {
        return mBookingsDao.getBookingsByTitleAsc(authorId);
    }

}
