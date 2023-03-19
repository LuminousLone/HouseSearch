package com.example.houseSearch.bean;

import java.util.Objects;

public class User {

    private String Name;
    private String Password;

    public User(String name,String password){
        this.Name = name;
        this.Password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Name, user.Name) && Objects.equals(Password, user.Password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Password);
    }
}
