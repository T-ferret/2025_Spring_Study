package goormton.backend.web1team.domain.note.application;

import goormton.backend.web1team.domain.note.domain.Note;
import goormton.backend.web1team.domain.note.domain.repository.NoteRepository;
import goormton.backend.web1team.domain.note.dto.CreateNoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public Note createNote(CreateNoteRequest request) {
        Note note = Note.builder()
                .title(request.title())
                .content(request.content())
                .build();
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Integer id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public Note updateNote(Integer id, CreateNoteRequest request) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다. id=" + id));

        Note updateNote = Note.builder()
                .id(note.getId())
                .title(request.title())
                .content(request.content())
                .build();
        return noteRepository.save(updateNote);
    }

    @Transactional
    public void deleteNote(Integer id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("유효하지 않은 아이디 값입니다. id=" + id);
        }
        noteRepository.deleteById(id);
    }
}
