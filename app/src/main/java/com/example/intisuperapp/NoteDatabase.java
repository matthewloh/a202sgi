package com.example.intisuperapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
// entities = {Note.class} is an array of entities, to add more entities, just add a comma and the next entity
public abstract class NoteDatabase extends RoomDatabase {
    // We create a singleton, so that we don't create multiple instances of the database
    private static NoteDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;

    // We create an executor service, so that we can run database operations in the background
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract NoteDao noteDao(); // Returns a NoteDao object, room takes care of the implementation

    // Synchronized means that only one thread at a time can access this method
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) { // Only want to instantiate this database if we dont have an instance
            // We use the builder pattern to create the database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() // This will delete the database if the version number is changed
                    .addCallback(sWordDatabaseCallBack) // This will populate the database with some dataN
                    .build();
        }
        return instance;
    }

    public static Callback sWordDatabaseCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.
                NoteDao dao = instance.noteDao();
                dao.deleteAllNotes();

                Note note = new Note("Title 1", "Description 1", 1);
                dao.insert(note);
                note = new Note("Title 2", "Description 2", 2);
                dao.insert(note);
                note = new Note("Title 3", "Description 3", 3);
                dao.insert(note);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                NoteDao dao = instance.noteDao();
                dao.deleteAllNotes();
                Note note = new Note("Title 1", "Description 1", 1);
                dao.insert(note);
                note = new Note("Title 2", "Description 2", 2);
                dao.insert(note);
                note = new Note("Title 3", "Description 3", 3);
                dao.insert(note);
            });
        }
    };
}
