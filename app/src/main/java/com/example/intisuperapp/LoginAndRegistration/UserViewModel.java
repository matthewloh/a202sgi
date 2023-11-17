package com.example.intisuperapp.LoginAndRegistration;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository mRepository;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

    // First Login Command - Check if the email exists
    public LiveData<User> getUserByEmail(String email) {
        return mRepository.getUserByEmail(email);
    }

    public void insert(User user) {
        mRepository.insert(user);
    }

    // TODO: Add a method to update the user's password, administrator-level only
}
