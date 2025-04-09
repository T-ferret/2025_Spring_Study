package goormton.backend.web1team.domain.note.domain.repository;

import goormton.backend.web1team.domain.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByOrderByCreatedAtDesc();
}
