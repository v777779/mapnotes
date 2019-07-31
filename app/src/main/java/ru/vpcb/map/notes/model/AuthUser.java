package ru.vpcb.map.notes.model;

public class AuthUser {
    private String uid;

    public AuthUser(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
