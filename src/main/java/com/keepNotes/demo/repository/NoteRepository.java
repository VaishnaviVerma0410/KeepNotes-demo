// package com.keepNotes.demo.repository;

// public class NoteRepository {
    
// }
package com.keepNotes.demo.repository;  //declares ehich folder the file belongs to

import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.data.jpa.repository.JpaRepository;
import com.keepNotes.demo.ObjectUser.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {  //this is declared as an interface, not a class - a contract with no implementation

}