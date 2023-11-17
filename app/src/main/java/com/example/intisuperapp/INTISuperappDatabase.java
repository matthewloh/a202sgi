package com.example.intisuperapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.Appointments.AppointmentDao;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitation;
import com.example.intisuperapp.Appointments.InviteAppointment.AppointmentInvitationDao;
import com.example.intisuperapp.Bookings.Bookings;
import com.example.intisuperapp.Bookings.BookingsDao;
import com.example.intisuperapp.LoginAndRegistration.User;
import com.example.intisuperapp.LoginAndRegistration.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Appointment.class, AppointmentInvitation.class, Bookings.class}, version = 2)
public abstract class INTISuperappDatabase extends RoomDatabase {
    // We create a singleton, so that we don't create multiple instances of the database
    private static INTISuperappDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;

    // We create an executor service, so that we can run database operations in the background
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();

    public abstract AppointmentDao appointmentDao();

    public abstract AppointmentInvitationDao appointmentInvitationDao();

    public abstract BookingsDao bookingsDao();

//    public abstract AppointmentUserJoinDao appointmentUserJoinDao();

    // Synchronized means that only one thread at a time can access this method
    public static synchronized INTISuperappDatabase getInstance(Context context) {
        if (instance == null) { // Only want to instantiate this database if we dont have an instance
            // We use the builder pattern to create the database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            INTISuperappDatabase.class, "intisuperapp_database")
                    .fallbackToDestructiveMigration() // This will delete the database if the version number is changed
                    .addCallback(sWordDatabaseCallBack) // This will populate the database with some dataN
                    .build();
        }
        return instance;
    }

    public static INTISuperappDatabase.Callback sWordDatabaseCallBack = new INTISuperappDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                UserDao userDao = instance.userDao();
                userDao.deleteAllUsers();

//                User user = new User("John696969 Doe", "j@.com", "12", "student");
//                userDao.insert(user);
//                user = new User("Jane Doe", "g", "12", "student");
//                userDao.insert(user);

//                AppointmentDao appointmentDao = instance.appointmentDao();
//                appointmentDao.deleteAllAppointments();
//                Date startDate = new Date();
//                Date endDate = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                try {
//                    startDate = sdf.parse("2020-12-12 12:12:12");
//                    endDate = sdf.parse("2020-12-12 12:12:12");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Appointment appointment = new Appointment(
//                        "Title",
//                        "Description",
//                        "Location",
//                        "Notes",
//                        startDate,
//                        endDate,
//                        1
//                );
//                appointmentDao.insert(appointment);
//                appointment = new Appointment(
//                        "Title 2",
//                        "Description 2",
//                        "Location 2",
//                        "Notes 2",
//                        startDate,
//                        endDate,
//                        1
//                );
//                appointmentDao.insert(appointment);

//                BookingsDao bookingsDao = instance.bookingsDao();
//                bookingsDao.deleteAllBookings();


            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
//                UserDao userDao = instance.userDao();
//                userDao.deleteAllUsers();
////
//                User user = new User("John Doe", "j@.com", "12", "student");
//                userDao.insert(user);
//                user = new User("Jane Doe", "g", "12", "student");
//                userDao.insert(user);
//////
////
//                AppointmentDao appointmentDao = instance.appointmentDao();
////                appointmentDao.deleteAllAppointments();
//
//                Date startDate = new Date();
//                Date endDate = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                try {
//                    startDate = sdf.parse("2020-12-12 12:12:12");
//                    endDate = sdf.parse("2020-12-12 12:12:12");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                User john = userDao.getUserByFullNameSync("John Doe");
//                Appointment appointment = new Appointment(
//                        "Title",
//                        "Description",
//                        "Location",
//                        "Notes",
//                        startDate,
//                        endDate,
//                        john.getId()
//                );
//                appointmentDao.insert(appointment);
//                appointment = new Appointment(
//                        "Title 2",
//                        "Description 2",
//                        "Location 2",
//                        "Notes 2",
//                        startDate,
//                        endDate,
//                        john.getId()
//                );
//                appointmentDao.insert(appointment);
//
//                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
//
//                BookingsDao bookingsDao = instance.bookingsDao();
////              bookingsDao.deleteAllBookings();
//
//                Date Date1 = new Date();
//                Date Date2 = new Date();
//
//                Bookings bookings = new Bookings("Title",Date1 , Date2, Date2, "012345678",  1);
//                bookingsDao.insert(bookings);
//                bookings = new Bookings("Title 2", Date1, Date2, Date1, "0164527895",  1);
//                bookingsDao.insert(bookings);

//                Date startDate = new Date();
//                Date endDate = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                try {
//                    startDate = sdf.parse("2020-12-12 12:12:12");
//                    endDate = sdf.parse("2020-12-12 12:12:12");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Appointment appointment = new Appointment(
//                        "Title 3",
//                        "Description 3",
//                        "Location 3",
//                        "Notes 3",
//                        startDate,
//                        endDate,
//                        jane.getId()
//                );
//                appointmentDao.insert(appointment);
            });
        }
    };
}