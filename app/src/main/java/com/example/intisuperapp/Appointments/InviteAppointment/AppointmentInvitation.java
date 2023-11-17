package com.example.intisuperapp.Appointments.InviteAppointment;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.intisuperapp.Appointments.Appointment;
import com.example.intisuperapp.DBUtils.Converters;
import com.example.intisuperapp.LoginAndRegistration.User;

import java.util.Date;


@Entity(tableName = "appointment_invitation",
        foreignKeys = {
                @ForeignKey(entity = Appointment.class, parentColumns = "id", childColumns = "appointment_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "invitee_id", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "author_id", onDelete = ForeignKey.CASCADE)
        })
@TypeConverters(Converters.class)
public class AppointmentInvitation {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @Embedded(prefix = "appointment_")
    private Appointment appointment;
    @Embedded(prefix = "author_")
    private User author;

    @ColumnInfo(name = "invite_status")
    public String inviteStatus;

    @ColumnInfo(name = "status_update_at")
    public Date statusUpdateAt;

    @ColumnInfo(name = "invitee_id")
    public int inviteeId;

    public AppointmentInvitation(Appointment appointment, User author, String inviteStatus, Date statusUpdateAt, int inviteeId) {
        this.appointment = appointment;
        this.author = author;
        this.inviteStatus = inviteStatus;
        this.statusUpdateAt = statusUpdateAt;
        this.inviteeId = inviteeId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Date getStatusUpdateAt() {
        return statusUpdateAt;
    }

    public void setStatusUpdateAt(Date statusUpdateAt) {
        this.statusUpdateAt = statusUpdateAt;
    }

    public int getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(int inviteeId) {
        this.inviteeId = inviteeId;
    }
}
