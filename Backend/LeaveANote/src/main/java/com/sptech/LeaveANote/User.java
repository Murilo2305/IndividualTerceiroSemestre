package com.sptech.LeaveANote;

public class User {

    private String name;
    private Integer id;
    private String email;
    private String senha;

    public User(String name, Integer id, String email, String senha) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
