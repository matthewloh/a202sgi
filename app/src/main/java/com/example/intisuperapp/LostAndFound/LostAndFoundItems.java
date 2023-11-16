package com.example.intisuperapp.LostAndFound;

import com.google.firebase.firestore.DocumentId;


public class LostAndFoundItems {

    @DocumentId
    private String documentId;
    private String itemName;
    private String contactInfo;
    private String lastLocation;
    private String itemDescription;
    private String itemImageURL;
    private String itemStatus;


    public LostAndFoundItems(String itemName, String contactInfo, String lastLocation, String itemDescription, String itemImageURL,  String itemStatus) {
        this.itemName = itemName;
        this.contactInfo = contactInfo;
        this.lastLocation = lastLocation;
        this.itemDescription = itemDescription;
        this.itemImageURL = itemImageURL;
        this.itemStatus = itemStatus;
    }


    public String getItemName() {return itemName;}

    public String getContactInfo() {return contactInfo;}

    public String getLastLocation() {return lastLocation;}

    public String getItemDescription() {return itemDescription;}

    public String getItemImageURL() {return itemImageURL;}

    public String getItemStatus() {return itemStatus;}
}
