package com.example.intisuperapp.LoginAndRegistration;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository mRepository;

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

}
