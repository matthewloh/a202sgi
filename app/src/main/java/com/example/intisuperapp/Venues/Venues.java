package com.example.intisuperapp.Venues;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.intisuperapp.DBUtils.Converters;

@Entity(tableName = "Venues")
@TypeConverters(Converters.class)
public class Venues {

    @PrimaryKey(autoGenerate = true)
    private int venueId;

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public Venues(String venueName, String venueImageURL){
        this.venueName = venueName;
        this.venueImageURL = venueImageURL;
    }

    private String venueName;
    private String venueImageURL;

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueImageURL() {
        return venueImageURL;
    }

    public void setVenueImageURL(String venueImageURL) {
        this.venueImageURL = venueImageURL;
    }
}
