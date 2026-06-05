package com.keepNotes.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.keepNotes.demo.ObjectUser.Note;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    public UserEntity() {
    }

    public UserEntity(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)  //one user can have many notes
    private List<Note> notes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
