package com.sptech.LeaveANote;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserModel {

    private String name;
    private Integer id;
    private String email;

    public UserModel(String name, Integer id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
