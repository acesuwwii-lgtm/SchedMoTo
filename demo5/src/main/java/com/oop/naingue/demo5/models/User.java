package com.oop.naingue.demo5.models;

import org.bson.Document;

public class User extends BaseModel {
    private String userName;
    private String email;
    private String phone;
    private String address;
    private String password; // always hashed in DB

    // Optional: temporary field to hold unhashed password in UI
    private transient String plainPassword;

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    @Override
    public Document toDocument() {
        Document doc = new Document();
        if (getId() != null) doc.append("_id", getId());
        doc.append("userName", userName);
        doc.append("email", email);
        doc.append("phone", phone);
        doc.append("address", address);
        doc.append("password", password); // hashed only
        return doc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
