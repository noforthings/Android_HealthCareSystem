package com.example.doctorhome.Models;

public class Disease {
    int id;
    String diseaseName;
    String description;
    int majorId;
    String image;

    public Disease(int id, String diseaseName, String description, int majorId, String image) {
        this.id = id;
        this.diseaseName = diseaseName;
        this.description = description;
        this.majorId = majorId;
        this.image = image;
    }
    public Disease(String diseaseName, String description, int majorId) {
        this.diseaseName = diseaseName;
        this.description = description;
        this.majorId = majorId;
    }

    public Disease(int id, String diseaseName) {
        this.id = id;
        this.diseaseName = diseaseName;
    }

    public Disease() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
