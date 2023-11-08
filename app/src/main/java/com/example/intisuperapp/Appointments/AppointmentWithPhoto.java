package com.example.intisuperapp.Appointments;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.Date;

public class AppointmentWithPhoto {
    @Embedded
    public Appointment appointment;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getTitle() {
        return appointment.getTitle();
    }

    public String getDescription() {
        return appointment.getDescription();
    }

    public String getLocation() {
        return appointment.getLocation();
    }

    public String getNotes() {
        return appointment.getNotes();
    }

    public Date getStartDate() {
        return appointment.getStartDate();
    }

    public Date getEndDate() {
        return appointment.getEndDate();
    }

    public int getAuthorId() {
        return appointment.getAuthorId();
    }

    public int getImageId() {
        return appointment.getImageId();
    }

    public int getId() {
        return appointment.getId();
    }
}
