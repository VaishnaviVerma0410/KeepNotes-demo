// package com.keepNotes.demo.repository;

// public class NoteRepository {
    
// }
package com.keepNotes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keepNotes.demo.ObjectUser.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}