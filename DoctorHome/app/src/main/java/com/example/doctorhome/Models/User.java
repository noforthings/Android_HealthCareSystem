package com.example.doctorhome.Models;

public class User {
    int id;
    String username;
    String password;
    String fullname;
    String email;
    String phone;
    int majorId;
    String major;
    String description;
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public User(int id, String username, String password, String fullname, String email, String phone, int majorId, String major, String description, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.majorId = majorId;
        this.major = major;
        this.description = description;
        this.image = image;
    }
    public User(int id, String username, String password, String fullname, String email, String phone, int majorId, String description, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.majorId = majorId;
        this.description = description;
        this.image = image;
    }

    public User() {
    }

    public User(int id, String username, String password, String fullname, String email, String phone, int majorId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.majorId = majorId;
    }

    public User(String username, String password, String email, int majorId, String fullname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.majorId = majorId;
        this.fullname = fullname;
    }

    public User(String fullname, String email, String phone, String description) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
    public User(String fullname, String email, String phone, String description, int majorId) {

        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.majorId = majorId;
        this.description= description;
    }
    public User(String fullname, String email, String phone, String description, int majorId, String major) {

        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.majorId = majorId;
        this.description= description;
        this.major = major;
    }
}
