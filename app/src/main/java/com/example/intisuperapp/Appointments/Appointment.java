package com.example.intisuperapp.Appointments;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.intisuperapp.DBUtils.Converters;
import com.example.intisuperapp.LoginAndRegistration.User;

import java.util.Date;

@Entity(tableName = "Appointment", foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "id", childColumns = "authorId", onDelete = ForeignKey.CASCADE)})
@TypeConverters(Converters.class)
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }


    public Appointment(String title, String description, String location, String notes, Date startDate, Date endDate, String apptStatus, String imageUrl, int authorId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.apptStatus = apptStatus;
        this.imageUrl = imageUrl;
        this.authorId = authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    private String description;

    private String location;

    private String notes;

    private Date startDate;

    private Date endDate;

    // "pending", "completed", "cancelled"
    @ColumnInfo(defaultValue = "pending")
    private String apptStatus;
    private String imageUrl;
    private int authorId;

    public String getApptStatus() {
        return apptStatus;
    }

    public void setApptStatus(String apptStatus) {
        this.apptStatus = apptStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

}
