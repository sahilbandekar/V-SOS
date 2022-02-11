package com.example.vsos;

public class UserClass {
    //isVerified=0 mean user is not verified
    private int isVerified = 0;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public UserClass() {
    }

    public UserClass(String email, String password, String name, String phoneNumber, int isVerified) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isVerified = isVerified;
    }

    public int getIsVerified() { return isVerified; }

    public void setIsVerified(int flag) { this.isVerified = flag; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
