package com.keepNotes.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.keepNotes.demo.ObjectUser.Note;
import com.keepNotes.demo.ObjectUser.ReorderNoteRequest;
import com.keepNotes.demo.ObjectUser.User;

import jakarta.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// @PathVariable: Information you give in the URL. Used when the value is the path of the URL itself
// @RestController: Tells Spring that this class handles HTTP requests and returns data (not views). It is also a shortcut for two annotations combined (@Controller, @ResponseBody)
// @RequestMapping: It defines the URL path. Ex: @RequestMapping("/notes"), every endpoint inside starts with /notes
// @RequestParam: data from query parameters. Used when the value comes after ? in the URL. Ex: priority?=3
// @RequestBody: data from the request body (JSON). Used when the client sends JSON data (POST / PUT).
// @RequestHeader: data from headers. Used when you need infor from HTTP headers.
// 2
@RestController
@RequestMapping("/users")
public class controllerDemo {

    User keepNotesApplication;
    private HashMap<String, User> users = new HashMap<>();

    // helper function to set ID for notes
    private void reindexNotes(User user) {
        if (user.getNotes() == null)
            return;
        for (int i = 0; i < user.getNotes().size(); i++) {
            user.getNotes().get(i).setId(i);
        }
    }

    // helper to avoid duplicate priority notes
    private boolean priorityExists(User user, int priority) {
        for (Note note : user.getNotes()) {
            if (note.getPriority() == priority) {
                return true;
            }
        }
        return false;
    }

    // Adding a user
    @PostMapping("addUser") // writes to memory
    public User addUser(@RequestBody User user) {
        if (user.getUserEmail() == null || user.getUserEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userEmail is required");
        }

        if (users.containsKey(user.getUserEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        LocalDateTime now = LocalDateTime.now(); //was getting null for date and time until figured out that it should be added while creating the user.
        for (Note note : user.getNotes()) { //Did think of it but didn't stress enough!!
            note.setCreatedAt(now);
            note.setUpdatedAt(now);
        }
        users.put(user.getUserEmail(), user);
        reindexNotes(user);
        return user;
    }

    // Get user
    @GetMapping("/{id}") // reads from memory
    public User getUser(@RequestParam String userEmail) {

        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userEmail is required and cannot be empty");
        }

        User user = users.get(userEmail);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        // user.getNotes().sort(Comparator.comparing(Note::getTitle)); // added
        // comparator function to sort the titles in ascending order
        // user.getNotes().sort(Comparator.comparing(Note::getBody)); //this line
        // overrides the sorting of titles.
        user.getNotes().sort(Comparator.comparingInt(Note::getPriority));
        reindexNotes(user);
        return users.get(userEmail);
    }

    // adding individual note to a particular user
    @PostMapping("/notes/addNote")
    public Note addNoteToUser(@RequestParam String userEmail, @RequestBody Note note) {
        User user = users.get(userEmail);
        // show "user does not exist instead of "no body returned for response if GET
        // requested after deleting"
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (note.getPriority() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority must be 1 or greater");
        }

        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note title is required");
        }

        if (note.getBody() == null || note.getBody().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note body is required");
        }

        if (user.getNotes() == null) {
            user.setNotes(new ArrayList<>());
        }

        if (priorityExists(user, note.getPriority())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Priority already exists for this user");
        }

        LocalDateTime now = LocalDateTime.now();
        note.setCreatedAt(now);
        note.setUpdatedAt(now);

        user.getNotes().add(note);
        reindexNotes(user);
        System.out.println("CreatedAt: " + note.getCreatedAt());
        return note;
    }

    // First learn how to remove user, then remove a note in the name of that user.
    // deleting a user
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@RequestParam String userEmail) {
        User user = users.get(userEmail);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        users.remove(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    // change mapping from userName to userEmail
    // deleting one note from the user
    @DeleteMapping("/notes/delete/{index}")
    public User deleteNote(@RequestParam String userEmail, @PathVariable int index) {
        User user = users.get(userEmail);

        // Give proper exception for delete Mapping
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        if (user.getNotes() == null || index < 0 || index >= user.getNotes().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid note index");
        }

        user.getNotes().remove(index);
        reindexNotes(user);
        return user;
    }

    // deletes all notes from user
    @DeleteMapping("/notes")
    public User deleteAllNotes(@RequestParam String userEmail) {
        User user = users.get(userEmail);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        user.getNotes().clear();
        return user;
    }

    // user should be able to re-order the notes in the way he wants one to come in
    // first. Do that on 11th
    // Letting the user update priority of his notes
    @PutMapping("notes/updatePriority/{index}")
    public User updatePriority(@RequestParam String userEmail, @PathVariable int index, @RequestParam int priority) {
        User user = users.get(userEmail);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (index < 0 || index >= user.getNotes().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid note index");
        }

        if (priority < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority must start from 1");
        }

        if (priorityExists(user, priority)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Priority already exists");
        }
        user.getNotes().get(index).setPriority(priority);
        user.getNotes().get(index).setUpdatedAt(LocalDateTime.now());
        return user;
    }

    // letting the user re-order the notes the way he see fits
    @PutMapping("notes/reorder") // this line is called a PUT request Mapping
    public User reorderNotes(@RequestParam String userEmail, @RequestBody ReorderNoteRequest request) {
        User user = users.get(userEmail); // get the desired user
        if (user == null) { // if user does not exist, display the message
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        List<Note> notes = user.getNotes(); // get the list of notes of that user.
        int fromIndex = request.getFromIndex(); // get from Index (previous index before changing)
        int toIndex = request.getToIndex(); // get to index (index where the user wants the note to be)

        if (fromIndex < 0 || fromIndex >= notes.size() || toIndex < 0 || toIndex >= notes.size()) { // if user is giving unexceptable index values, display this message
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid note Index");
        }

        Note noteToMove = notes.remove(fromIndex); // picking the note to move
        notes.add(toIndex, noteToMove); // putting the note picked into the new position    
        reindexNotes(user);
        return user;
    }

    private void validateNote(Note note) {
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note title is required");
        }
        if (note.getBody() == null || note.getBody().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note body is required");
        }
        if (note.getPriority() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority must be 1 or greater");
        } 
    }

    @PutMapping("/{email}/notes/{index}")
    public Note updateNote(@PathVariable String email, @PathVariable int index, @RequestBody Note updatedNote) {
        User user = users.get(email);
        System.out.println("Updating note for user: " + email + " at index: " + index);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (index < 0 || index >= user.getNotes().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid note index");
        }

        Note existingNote = user.getNotes().get(index);
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setBody(updatedNote.getBody());
        existingNote.setPriority(updatedNote.getPriority());
        validateNote(existingNote);
        existingNote.setUpdatedAt(LocalDateTime.now());
        reindexNotes(user);
        return existingNote;       //return just the note, not needed to return the entire user
    }

    private Note getValidatedNote(String email, int index) {
        User user = users.get(email);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (index < 0 || index >= user.getNotes().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note index invalid");
        }
        return user.getNotes().get(index);
    }

    @PatchMapping("/{email}/notes/{index}/pin")
    public Note pinNote(@PathVariable String email, @PathVariable int index) {

        Note note = getValidatedNote(email, index);
        note.setPinned(true);
        note.setUpdatedAt(LocalDateTime.now());
        return note;
    }

    @PatchMapping("/{email}/notes/{index}/unpin")
    public Note unPinNote(@PathVariable String email, @PathVariable int index) {

        Note note = getValidatedNote(email, index);
        note.setPinned(false);
        note.setUpdatedAt(LocalDateTime.now());
        return note;
    }

    @GetMapping("/notes/search")
    public List<Note> searchNotes(@RequestParam String userEmail, @RequestParam String q) {
        User user = users.get(userEmail);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (user.getNotes() == null) {
            return new ArrayList<>();
        }

        String search = q.toLowerCase();
        List<Note> result = new ArrayList<>();

        for (Note note: user.getNotes()) {
            if (note.getTitle().toLowerCase().contains(search) || note.getBody().toLowerCase().contains(search)) {
                result.add(note);
            }
        }
        return result;
    }
}
