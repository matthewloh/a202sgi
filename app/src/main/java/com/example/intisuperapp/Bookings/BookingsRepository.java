package com.example.intisuperapp.Bookings;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;

public class BookingsRepository {
    private BookingsDao mBookingsDao;
    private LiveData<List<Bookings>> mAllBookings;

    public BookingsRepository(Application application) {
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

    public LiveData<List<Bookings>> getBookingsByStartTime(int authorId) {
        return mBookingsDao.getBookingsByStartTime(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByStartTimeAsc(int authorId) {
        return mBookingsDao.getBookingsByStartTimeAsc(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByVenue(int authorId) {
        return mBookingsDao.getBookingsByVenue(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByVenueAsc(int authorId) {
        return mBookingsDao.getBookingsByVenueAsc(authorId);
    }

    public LiveData<List<Bookings>> getBookingsByDateTimeVenue(String venue, String date, String startTime, String endTime) {
        return mBookingsDao.getBookingsByDateTimeVenue(venue, date, startTime, endTime);
    }

    public void updateBookingsById(String venue, String date, String startTime, String endTime, String contact, int userId, int id) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mBookingsDao.updateBookingsById(venue, date, startTime, endTime, contact, userId, id));
    }


    public LiveData<List<Bookings>> getBookingsByDateVenue(String venue, String date) {
        return mBookingsDao.getBookingsByDateVenue(venue, date);
    }
}
