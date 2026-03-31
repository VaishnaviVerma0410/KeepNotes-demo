package com.keepNotes.demo.ObjectUser;
import java.util.ArrayList;
import java.util.List;

public class User {
    public String userName;
    public String userEmail;
    private List<Note> notes = new ArrayList<>();


    public User (String userName, String userEmail, List<Note> notes) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.notes = notes;
    }

    public User() {
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
        this.userEmail =userEmail;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void remove(String userName2) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
 }
