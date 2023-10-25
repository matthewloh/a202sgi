package com.example.intisuperapp.LoginAndRegistration;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.intisuperapp.INTISuperappDatabase;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        INTISuperappDatabase database = INTISuperappDatabase.getInstance(application);
        mUserDao = database.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    // This helps us use ExecutorService to run database operations in the background thread
    // These APIs are exposed to the ViewModel so that it can call them
    // only from this repository, not from the database directly
    public void insert(User user) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mUserDao.insert(user));
    }

    public void update(User user) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mUserDao.update(user));
    }

    public LiveData<User> queryUserByEmailAndPassword(String email, String password) {
        return mUserDao.queryUserByEmailAndPassword(email, password);
    }

    public LiveData<User> getUserById(int id) {
        return mUserDao.getUserById(id);
    }

    public LiveData<User> getUserByEmail(String email) {
        return mUserDao.getUserByEmail(email);
    }

    public void deleteAllUsers() {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mUserDao.deleteAllUsers());
    }

    public int getUserCount() {
        return mUserDao.getUserCount();
    }

    public void upsert(User user) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mUserDao.upsert(user));
    }

    public void deleteUserById(int id) {
        INTISuperappDatabase.databaseWriteExecutor.execute(() -> mUserDao.deleteUserById(id));
    }

}
