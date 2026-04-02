package com.keepNotes.demo.ObjectUser;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Note {

    String title;
    String body;
    int priority; //lesser the number, higher the priority
    int id;
    private boolean isPinned = false;
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedAt;

    public Note() {
    }

    public Note(String title, String body, int priority) {
        this.title = title;
        this.body = body;
        this.priority = priority;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }
    
    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public LocalDateTime getCreatedAt() {   //only getter for createdAt so it never gets updated again and again
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
