package com.example.intisuperapp.LostAndFound;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.intisuperapp.DBUtils.Converters;

@Entity(tableName = "LostItems")
@TypeConverters(Converters.class)
public class LostAndFoundItems {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {return id;}

    public LostAndFoundItems(String itemname, String methodofcontact, String lastlocation, String description)
    {
        this.itemname = itemname;
        this.methodofcontact = methodofcontact;
        this.lastlocation = lastlocation;
        this.description = description;
    }

    public void setId(int id) {this.id = id;}
    private String itemname;

    private String methodofcontact;

    private String lastlocation;

    private String description;

    private String category;

    public String getItemname() {return itemname;}

    public void setItemname(String itemname) {this.itemname = itemname;}

    public String getMethodofcontact() {return methodofcontact;}

    public void setMethodofcontact(String methodofcontact) {this.methodofcontact = methodofcontact;}

    public String getLastlocation() {return lastlocation;}

    public void setLastlocation(String lastlocation) {this.lastlocation = lastlocation;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
}
