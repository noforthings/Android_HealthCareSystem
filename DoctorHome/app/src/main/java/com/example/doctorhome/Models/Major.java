package com.example.doctorhome.Models;

public class Major {
    int id;
    String majorName;
    String description;
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Major() {
    }

    public Major(int id, String majorName, String description, String image) {
        this.id = id;
        this.majorName = majorName;
        this.description = description;
        this.image = image;
    }

    public Major(int id, String majorName) {
        this.id = id;
        this.majorName = majorName;
    }
}
