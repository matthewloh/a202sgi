package com.example.intisuperapp.LostAndFound;

import com.google.firebase.firestore.Blob;


public class LostAndFoundItems {

    private String itemName;
    private String contactInfo;
    private String lastLocation;
    private String itemDescription;
    private String itemImageURL;
    private Blob itemImage;
    private String itemStatus;


    public LostAndFoundItems(String itemName, String contactInfo, String lastLocation, String itemDescription, String itemImageURL, Blob itemImage, String itemStatus) {
        this.itemName = itemName;
        this.contactInfo = contactInfo;
        this.lastLocation = lastLocation;
        this.itemDescription = itemDescription;
        this.itemImageURL = itemImageURL;
        this.itemImage = itemImage;
        this.itemStatus = itemStatus;
    }


    public String getItemName() {return itemName;}

    public String getContactInfo() {return contactInfo;}

    public String getLastLocation() {return lastLocation;}

    public String getItemDescription() {
        return itemDescription;
    }


    public String getItemImageURL() {return itemImageURL;}

    public Blob getItemImage() {
        return itemImage;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setLost() {
        this.itemStatus = "Lost";
    }

    public void setFound() {
        this.itemStatus = "Found";
    }
}
