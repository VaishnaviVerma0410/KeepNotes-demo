package com.keepNotes.demo.ObjectUser;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String userEmail;
    private List<Note> notes = new ArrayList<>();

    public User() {
    }

    public User(String userName, String userEmail, List<Note> notes) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}