package com.oop.naingue.demo5.data;

public class UserData {
    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;

    public UserData() {}

    public UserData(String username, String email, String phone, String address, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }


    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getPassword() { return password; }
}
