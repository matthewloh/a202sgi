package com.example.intisuperapp.Events;

import com.google.firebase.firestore.DocumentId;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventData {

    @DocumentId
    private String documentId;

    private Integer eventID;

    private String eventName;
    private String eventVenue;
    private Date eventDate;
    private Date eventStartTime;
    private Date eventEndTime;
    private String eventDescription;
    private String eventImageURL;

    public EventData() {
        //No-Arguement constructor required for Firestore deserialization
    }

    public EventData(Integer eventID, String eventName, String eventVenue, Date eventDate, Date eventStartTime, Date eventEndTime, String eventDescription, String eventImageURL) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventDescription = eventDescription;
        this.eventImageURL = eventImageURL;
    }

    public Integer getEventID() {return eventID;}

    public String getEventName() {return eventName;}

    public String getEventVenue() {return eventVenue;}

    public String getEventDescription() {return eventDescription;}

    public String getEventImageURL() {return eventImageURL;}

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        return sdf.format(eventDate);
    }

    public String getFormattedStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(eventStartTime);
    }

    public String getFormattedEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(eventEndTime);
    }


}
