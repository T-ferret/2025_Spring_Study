package goormton.backend.web1team.domain.note.dto;

import goormton.backend.web1team.domain.note.domain.Note;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "노트 정보 조회용 responseDTO입니다.")
public record NoteResponse(
        @Schema(description = "노트 객체 id")
        Integer id,
        @Schema(description = "노트 타이틀")
        String title,
        @Schema(description = "노트 내용")
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
