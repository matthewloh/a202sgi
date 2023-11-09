package com.example.intisuperapp.Appointments;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.intisuperapp.LoginAndRegistration.User;


// TODO: 2023-11-10 ALlow users to create appointments in a join table
@Entity(tableName = "appointment_user_join",
        primaryKeys = {"userId", "appointmentId"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Appointment.class,
                        parentColumns = "id",
                        childColumns = "appointmentId")
        }
)
public class AppointmentUserJoin {
    public int userId;
    public int appointmentId;

    public String inviteStatus;

    public AppointmentUserJoin(int userId, int appointmentId, String inviteStatus) {
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.inviteStatus = inviteStatus;
    }
}