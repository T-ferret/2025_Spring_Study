package goormton.backend.web1team.domain.note.presentation;

import goormton.backend.web1team.domain.note.application.NoteService;
import goormton.backend.web1team.domain.note.domain.Note;
import goormton.backend.web1team.domain.note.dto.CreateNoteRequest;
import goormton.backend.web1team.domain.note.dto.NoteResponse;
import goormton.backend.web1team.global.payload.ResponseCustom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCustom<?> createNote(@Valid @RequestBody CreateNoteRequest request) {
        Note note = noteService.createNote(request);
        NoteResponse response = NoteResponse.fromEntity(note);

        return ResponseCustom.CREATED(response);
    }

    @GetMapping
    public ResponseCustom<?> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        List<NoteResponse> responses = notes.stream().map(NoteResponse::fromEntity).toList();

        return ResponseCustom.OK(responses);
    }

    @GetMapping("/{id}")
    public ResponseCustom<?> getNoteById(@PathVariable Integer id) {
        Note note = noteService.getNoteById(id);
        NoteResponse response = NoteResponse.fromEntity(note);

        return ResponseCustom.OK(response);
    }

    @PutMapping("/{id}")
    public ResponseCustom<?> updateNote(@PathVariable Integer id, @Valid @RequestBody CreateNoteRequest request) {
        Note note = noteService.updateNote(id, request);
        NoteResponse response = NoteResponse.fromEntity(note);

        return ResponseCustom.OK(response);
    }

    @DeleteMapping("/{id}")
    public ResponseCustom<?> deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return ResponseCustom.OK();
    }
}
