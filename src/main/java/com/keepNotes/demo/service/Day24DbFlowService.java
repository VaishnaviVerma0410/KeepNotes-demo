package com.keepNotes.demo.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.keepNotes.demo.ObjectUser.Note;
import com.keepNotes.demo.dto.CreateNoteRequest;
import com.keepNotes.demo.dto.CreateUserRequest;
import com.keepNotes.demo.entity.UserEntity;
import com.keepNotes.demo.repository.NoteRepository;
import com.keepNotes.demo.repository.UserRepository;

@Service
public class Day24DbFlowService {

    private final UserRepository userRepository;  //final means assigned once
    private final NoteRepository noteRepository;

    public Day24DbFlowService(UserRepository userRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    public UserEntity createUser(CreateUserRequest request) {  //creating a new user in database
        if (request.getUserEmail() == null || request.getUserEmail().trim().isEmpty()) {  //if user provided empty or wrong email, throw exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userEmail is required");
        }

        if (userRepository.existsByUserEmail(request.getUserEmail())) {   //if user already exists, throw exception
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        UserEntity userEntity = new UserEntity(request.getUserName(), request.getUserEmail());  //making a user entity with userName and userEmail
        return userRepository.save(userEntity);   //saves user in DB and returns saved object
    }

    public Note addNote(String email, CreateNoteRequest request) {   //function to add a note for a given email
        UserEntity user = userRepository.findByUserEmail(email)   //trying to find the user by the given email
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));   //if the user is not found, hrow exception

        validateNoteRequest(request);  //validates note input (title, body, priority)

        Note note = new Note(request.getTitle(), request.getBody(), request.getPriority());   
        note.setUser(user);

        LocalDateTime now = LocalDateTime.now();
        note.setCreatedAt(now);
        note.setUpdatedAt(now);

        return noteRepository.save(note);
    }

    public List<Note> getNotes(String email) {
        if (!userRepository.existsByUserEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<Note> notes = noteRepository.findByUser_UserEmail(email);
        notes.sort(Comparator.comparingInt(Note::getPriority));
        return notes;
    }

    private void validateNoteRequest(CreateNoteRequest request) {   //helper function to validate the title, body and priority of the user
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note title is required");
        }

        if (request.getBody() == null || request.getBody().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note body is required");
        }

        if (request.getPriority() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority must be 1 or greater");
        }
    }
}
