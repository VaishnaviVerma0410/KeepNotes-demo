// package com.keepNotes.demo.repository;

// public class NoteRepository {
    
// }
package com.keepNotes.demo.repository;  //declares ehich folder the file belongs to

import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.data.jpa.repository.JpaRepository;
import com.keepNotes.demo.ObjectUser.Note;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {  //this is declared as an interface, not a class - a contract with no implementation
    List<Note> findByUser_UserEmail(String userEmail);  //it will use email to identify the notes that belongs to a user

}