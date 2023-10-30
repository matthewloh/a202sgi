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

    // Second Login Command - After getting the email, check if the password is correct
    public LiveData<User> getUserByEmailAndPassword(String email, String password) {
        return mRepository.queryUserByEmailAndPassword(email, password);
    }

    public LiveData<User> getUserByFullName(String fullName) {
        return mRepository.getUserByFullName(fullName);
    }

    // Sign up button
    public void insert(User user) {
        mRepository.insert(user);
    }
}
