// package com.keepNotes.demo.ObjectUser;
// import java.time.LocalDateTime;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.fasterxml.jackson.annotation.JsonProperty.Access;

// public class Note {

//     String title;
//     String body;
//     int priority; //lesser the number, higher the priority
//     int id;
//     private boolean isPinned = false;
//     @JsonProperty(access = Access.READ_ONLY)
//     private LocalDateTime createdAt;

//     @JsonProperty(access = Access.READ_ONLY)
//     private LocalDateTime updatedAt;
//     private boolean isArchived = false;
//     private boolean isTrashed = false;

//     public Note() {
//     }

//     public Note(String title, String body, int priority) {
//         this.title = title;
//         this.body = body;
//         this.priority = priority;
//     }

package com.keepNotes.demo.ObjectUser;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;

@Entity   //tells spring that this class should be stored as a table in the database
public class Note {

    @Id   //marks primary key (unique identifier)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // changed from int → Long (important)

    private String title;
    private String body;
    private int priority;

    private boolean isPinned = false;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedAt;

    private boolean isArchived = false;
    private boolean isTrashed = false;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isArchived() {
        return isArchived;
    }
    
    public void setArchived(boolean archived) {
        this.isArchived = archived;
    }

    public boolean isTrashed() {
        return isTrashed;
    }

    public void setTrashed(boolean trashed) {
        this.isTrashed = trashed;
    }
}
