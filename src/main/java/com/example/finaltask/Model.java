package com.example.finaltask;


import android.graphics.Bitmap;

public class Model {
    String id;
    Bitmap image;
    String title;
    String description;
    String date;
    String time;
    String quantity;
    String location;

    public Model(String id, Bitmap image, String title, String description, String date, String time, String quantity, String location) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
