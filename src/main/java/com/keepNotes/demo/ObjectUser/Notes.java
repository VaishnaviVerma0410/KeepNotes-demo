package com.keepNotes.demo.ObjectUser;

import java.util.List;

public class Notes {

    private List<Note> notes;
    private String userName;

    public Notes (String userName, List<Note> notes) {
        this.userName = userName;
        this.notes = notes; 
    }

    public String getUserName() {
        return userName;
    }
    
    public List<Note> getNotes() {
        return notes;
    }
    
}
