package com.sptech.LeaveANote;

public class Note {

    private Integer id;
    private Integer fkUser;
    private String msg;

    public Note(Integer id, Integer fkUser, String mesg) {
        this.id = id;
        this.fkUser = fkUser;
        this.msg = mesg;
    }

    public Note() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkUser() {
        return fkUser;
    }

    public void setFkUser(Integer fkUser) {
        this.fkUser = fkUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
