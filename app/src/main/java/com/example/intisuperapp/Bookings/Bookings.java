package com.example.intisuperapp.Bookings;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.intisuperapp.DBUtils.Converters;
import com.example.intisuperapp.LoginAndRegistration.User;

import java.util.Date;

@Entity(tableName = "Bookings", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "authorId", onDelete = ForeignKey.CASCADE))
@TypeConverters(Converters.class)
public class  Bookings {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public Bookings(String venue, String date, String startTime,
                    String endTime, String contact, int authorId){
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contact = contact;
        this.authorId = authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String venue;
    private String date;
    private String startTime;
    private String endTime;
    private String contact;
    private int authorId;

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
