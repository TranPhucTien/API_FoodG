package com.nhom7.foodg.models.entities;

public class MapEntity {
    String duration; // TG
    String distance; // KC
    String img; //<iframe>

    public MapEntity(String duration, String distance, String img) {
        this.duration = duration;
        this.distance = distance;
        this.img = img;
    }

    public MapEntity() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}