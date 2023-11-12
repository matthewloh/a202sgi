package com.example.intisuperapp.LoginAndRegistration;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    // Single user insert
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    // Checking user exists already in the database
    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    LiveData<User> queryUserByEmailAndPassword(String email, String password);

    // Retrieve user details by id
    @Query("SELECT * FROM User WHERE id = :id")
    LiveData<User> getUserById(int id);

    // Retrieve user details by email
    @Query("SELECT * FROM User WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("DELETE FROM User")
    void deleteAllUsers();

    @Query("SELECT COUNT(*) FROM User")
    int getUserCount();

    // Upsert user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(User user);

    // Delete user
    @Query("DELETE FROM User WHERE id = :id")
    void deleteUserById(int id);

    @Query("SELECT * FROM User ORDER by id DESC")
    LiveData<List<User>> getAllUsers();

    // Get use by name
    @Query("SELECT * FROM User WHERE full_name = :name")
    LiveData<User> getUserByFullName(String name);

    // Get use by name
    @Query("SELECT * FROM User WHERE full_name = :name")
    User getUserByFullNameSync(String name);

    @Query("SELECT * FROM User WHERE full_name LIKE '%' || :name || '%'")
    LiveData<List<User>> getUserByFullNameLike(String name);

    // Get all Students
    @Query("SELECT * FROM User WHERE role = 'student'")
    LiveData<List<User>> getAllStudents();

    // Get all Teachers
    @Query("SELECT * FROM User WHERE role = 'lecturer'")
    LiveData<List<User>> getAllLecturers();
}
