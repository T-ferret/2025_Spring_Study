package goormton.backend.web1team.domain.note.domain.repository;

import goormton.backend.web1team.domain.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
