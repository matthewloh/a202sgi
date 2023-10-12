package com.example.intisuperapp.OldNotes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        mNoteDao = database.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }


    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    // This helps us use ExecutorService to run database operations in the background thread
    // These APIs are exposed to the ViewModel so that it can call them
    // only from this repository, not from the database directly
    public void insert(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> mNoteDao.insert(note));
    }

    public void update(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> mNoteDao.update(note));
    }

    public void delete(Note note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> mNoteDao.delete(note));
    }

    public void deleteAllNotes() {
        NoteDatabase.databaseWriteExecutor.execute(() -> mNoteDao.deleteAllNotes());
    }
}