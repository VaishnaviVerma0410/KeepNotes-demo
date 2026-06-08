package com.keepNotes.demo.dto;   //DTO: Data transfer Object Folder
//DTOs allow what data can come in and go out of the API, they protect the real database model from direct client control.
//DTO folder was created and Note.java was directly not used because Note.java has lot more other fields that shouldn't be controlled by the client.  
public class CreateNoteRequest {  

    private String title;
    private String body;
    private int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
