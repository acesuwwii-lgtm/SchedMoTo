package com.oop.naingue.demo5.mainmenu;

import org.bson.types.ObjectId;

/**
 * Singleton class to store information about the currently logged-in user.
 * It allows easy access to user data across controllers without re-passing data manually.
 */
public class CurrentUser {

    // Static instance (Singleton)
    private static CurrentUser instance;

    // User details
    private ObjectId customerId;
    private String fullName;
    private String contactInfo;
    private boolean loggedIn;

    /**
     * Private constructor — prevents external instantiation.
     */
    private CurrentUser() {
        this.loggedIn = false;
    }

    /**
     * Returns the single instance of CurrentUser.
     */
    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    // -----------------------
    // Getters and Setters
    // -----------------------

    // Option 1: Return ObjectId (recommended)
    public ObjectId getCustomerId() {
        return customerId;
    }

    // Option 2: Return String representation (alternative)
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

    /**
     * Logs in a user and stores their basic info.
     */
    public void login(ObjectId id, String name, String contact) {
        this.customerId = id;
        this.fullName = name;
        this.contactInfo = contact;
        this.loggedIn = true;
        System.out.println("✅ Logged in as: " + name + " (" + id + ")");
    }

    /**
     * Logs out the current user and clears data.
     */
    public void logout() {
        this.customerId = null;
        this.fullName = null;
        this.contactInfo = null;
        this.loggedIn = false;
        System.out.println("🚪 User logged out.");
    }

    /**
     * Debug helper: display current user details.
     */
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