package com.sptech.LeaveANote;


import java.time.LocalDate;

public class UserModel {

    private String name;
    private Integer id;
    private String email;
    private LocalDate birth;
    private String function;
    private String genre;
    private Boolean recieveEMails;

    public UserModel(String name, Integer id, String email, LocalDate birth, String function, String genre, Boolean recieveEMails) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.birth = birth;
        this.function = function;
        this.genre = genre;
        this.recieveEMails = recieveEMails;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Boolean getRecieveEmails() {
        return recieveEMails;
    }

    public void setRecieveEMails(Boolean recieveEMails) {
        this.recieveEMails = recieveEMails;
    }
}
