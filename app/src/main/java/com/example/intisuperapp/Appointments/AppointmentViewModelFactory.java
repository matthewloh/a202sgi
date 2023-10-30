package com.example.intisuperapp.Appointments;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

public class AppointmentViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private final int mAuthorId;

    public AppointmentViewModelFactory(Application application, int authorId) {
        mApplication = application;
        mAuthorId = authorId;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        return (T) new AppointmentViewModel(mApplication, mAuthorId);
    }
}
