package com.oop.naingue.demo5.mainmenu;

import org.bson.types.ObjectId;

public class CurrentUser {

    private static CurrentUser instance;

    private ObjectId customerId;
    private String fullName;
    private String contactInfo;
    private boolean loggedIn;

    private CurrentUser() {
        this.loggedIn = false;
    }

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public ObjectId getCustomerId() {
        return customerId;
    }

    public String getCustomerIdAsString() {
        return customerId != null ? customerId.toString() : null;
    }

    public void setCustomerId(ObjectId customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName != null ? fullName : "Guest";
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactInfo() {
        return contactInfo != null ? contactInfo : "N/A";
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void login(ObjectId id, String name, String contact) {
        this.customerId = id;
        this.fullName = name;
        this.contactInfo = contact;
        this.loggedIn = true;
        System.out.println("Logged in as: " + name + " (" + id + ")");
    }

    public void logout() {
        this.customerId = null;
        this.fullName = null;
        this.contactInfo = null;
        this.loggedIn = false;
        System.out.println("User logged out.");
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
}