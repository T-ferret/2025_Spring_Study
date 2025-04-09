package goormton.backend.web1team.domain.note.dto.response;

import goormton.backend.web1team.domain.note.domain.Note;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "노트 정보 조회용 responseDTO입니다.")
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
