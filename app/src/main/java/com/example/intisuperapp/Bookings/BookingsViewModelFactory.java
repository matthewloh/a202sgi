package com.example.intisuperapp.Bookings;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

public class BookingsViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private final int mAuthorId;

    public BookingsViewModelFactory(Application application, int authorId) {
        mApplication = application;
        mAuthorId = authorId;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        return (T) new BookingsViewModel(mApplication, mAuthorId);
    }
}
