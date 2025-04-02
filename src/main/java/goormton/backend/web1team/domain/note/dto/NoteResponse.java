package goormton.backend.web1team.domain.note.dto;

import goormton.backend.web1team.domain.note.domain.Note;

public record NoteResponse(
        Integer id,
        String title,
        String content
) {

    public static NoteResponse fromEntity(Note entity) {
        if (entity == null) {
            return null;
        }
        return new NoteResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
