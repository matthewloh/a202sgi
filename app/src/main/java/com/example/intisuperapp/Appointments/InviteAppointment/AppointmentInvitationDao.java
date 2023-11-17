package com.example.intisuperapp.Appointments.InviteAppointment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppointmentInvitationDao {
    @Insert
    void insert(AppointmentInvitation appointmentInvitation);

    @Update
    void update(AppointmentInvitation appointmentInvitation);

    @Query("UPDATE appointment_invitation SET invite_status = :status WHERE id = :invitationId")
    void updateInvitationStatus(int invitationId, String status);

    @Delete
    void delete(AppointmentInvitation appointmentInvitation);

    @Query("SELECT * FROM appointment_invitation WHERE appointment_id = :appointment_id")
    LiveData<List<AppointmentInvitation>> getAppointmentInvitationByAppointmentId(int appointment_id);

    @Query("SELECT * FROM appointment_invitation WHERE invitee_id = :invitee_id")
    LiveData<List<AppointmentInvitation>> getAppointmentInvitationByInviteeId(int invitee_id);

    @Query("SELECT * FROM appointment_invitation WHERE invitee_id = :invitee_id AND invite_status = :status")
    LiveData<List<AppointmentInvitation>> getAppointmentInvitationByInviteeIdAndStatus(int invitee_id, String status);

    @Query("SELECT * FROM appointment_invitation WHERE invitee_id = :invitee_id AND invite_status = 'pending'")
    LiveData<List<AppointmentInvitation>> getPendingAppointmentInvitationByInviteeId(int invitee_id);

    @Query("SELECT * FROM appointment_invitation WHERE appointment_id = :appointmentId AND invitee_id = :userId")
    LiveData<AppointmentInvitation> getAppointmentInvitationByAppointmentIdAndUserId(int appointmentId, int userId);

    @Query("DELETE FROM appointment_invitation WHERE appointment_id = :appointmentId AND invitee_id = :userId")
    void deleteByAppointmentIdAndUserId(int appointmentId, int userId);
}
