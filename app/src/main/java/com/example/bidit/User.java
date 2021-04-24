package com.example.bidit;

public class User {

    private String email;
    private String fullName;
    private String adress;
    private String phone;
    private String password;
    private String userType;

    public User() {}


    public User(String fullName, String adress, String phone,
                String email,String password) {
        this.email = email;
        this.fullName = fullName;
        this.adress = adress;
        this.phone = phone;
        this.userType = userType;

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}