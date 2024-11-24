package mr.demonid.spring.hw6.repository;

import mr.demonid.spring.hw6.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findNotesByTitle(String title);
}
