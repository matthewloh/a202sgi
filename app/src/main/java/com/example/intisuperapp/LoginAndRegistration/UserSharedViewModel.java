package com.example.intisuperapp.LoginAndRegistration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserSharedViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();

    public void setUser(User user) {
        this.user.setValue(user);
    }

    public LiveData<User> getUser() {
        return user;
    }
}
