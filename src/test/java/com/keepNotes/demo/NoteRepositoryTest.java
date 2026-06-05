package com.keepNotes.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.keepNotes.demo.ObjectUser.Note;
import com.keepNotes.demo.entity.UserEntity;
import com.keepNotes.demo.repository.NoteRepository;
import com.keepNotes.demo.repository.UserRepository;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:noterepositorytest;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class NoteRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void saveNote_withUser_persistsRelationship() {
        String email = "note-user-" + UUID.randomUUID() + "@example.com";

        UserEntity user = new UserEntity("Vaishnavi", email);
        UserEntity savedUser = userRepository.save(user);

        Note note = new Note("Study JPA", "Learn user-note relationship", 1);
        note.setUser(savedUser);

        Note savedNote = noteRepository.save(note);

        assertThat(savedNote.getId()).isNotNull();
        assertThat(savedNote.getUser()).isNotNull();
        assertThat(savedNote.getUser().getUserEmail()).isEqualTo(email);
    }

    @Test
    void findByUserEmail_returnsOnlyThatUsersNotes() {
        String email = "find-notes-" + UUID.randomUUID() + "@example.com";

        UserEntity user = userRepository.save(new UserEntity("Vaishnavi", email));

        Note noteOne = new Note("First note", "Body one", 1);
        noteOne.setUser(user);

        Note noteTwo = new Note("Second note", "Body two", 2);
        noteTwo.setUser(user);

        noteRepository.save(noteOne);
        noteRepository.save(noteTwo);

        List<Note> notes = noteRepository.findByUser_UserEmail(email);

        assertThat(notes).hasSize(2);
        assertThat(notes)
            .extracting(Note::getTitle)
            .containsExactlyInAnyOrder("First note", "Second note");
    }
}