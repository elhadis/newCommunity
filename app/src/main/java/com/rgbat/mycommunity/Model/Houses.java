package com.rgbat.mycommunity.Model;

public class Houses {
    private String houseimage,description,place,price,phone ,date,time;

    public Houses() {
    }

    public Houses(String houseimage, String description, String place, String price, String phone, String date, String time) {
        this.houseimage = houseimage;
        this.description = description;
        this.place = place;
        this.price = price;
        this.phone = phone;
        this.date = date;
        this.time = time;
    }

    public String getHouseimage() {
        return houseimage;
    }

    public void setHouseimage(String houseimage) {
        this.houseimage = houseimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
