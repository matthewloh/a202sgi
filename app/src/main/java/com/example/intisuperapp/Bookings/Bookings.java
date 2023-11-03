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
public class Bookings {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public Bookings(String venue, Date date, Date startTime,
                    Date endTime, String contact, int authorId){
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
    private Date date;
    private Date startTime;
    private Date endTime;
    private String contact;
    private int authorId;

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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
