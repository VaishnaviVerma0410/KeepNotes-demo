package com.keepNotes.demo.ObjectUser;

public class Note {

    String title;
    String body;
    int priority; //lesser the number, higher the priority
    int id;

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
}
