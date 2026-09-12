package com.sptech.LeaveANote;

import java.sql.Date;
import java.time.LocalDate;

public class User {

    private String name;
    private Integer id;
    private String email;
    private String senha;
    private LocalDate birth;
    private String function;
    private String genre;
    private Boolean recieveEmails;

    public User(String name, Integer id, String email, String senha, LocalDate birth, String function, String genre, Boolean recieveEMails) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.birth = birth;
        this.function = function;
        this.genre = genre;
        this.recieveEmails = recieveEMails;
    }

    public User() {
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

    public String getPassword() {
        return senha;
    }

    public void setPassword(String senha) {
        this.senha = senha;
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
        return recieveEmails;
    }

    public void setRecieveEmails(Boolean recieveEmails) {
        this.recieveEmails = recieveEmails;
    }
}
