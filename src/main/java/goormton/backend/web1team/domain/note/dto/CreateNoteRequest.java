package goormton.backend.web1team.domain.note.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record CreateNoteRequest(
        @NotEmpty(message = "제목은 필수 입력 값입니다.")
        @Size(max = 100, message = "최대 100자까지 입력 가능합니다.")
        String title,

        @NotEmpty(message = "내용은 필수 입력 값입니다.")
        String content
) {

        @Builder
        public CreateNoteRequest(String title, String content) {
                this.title = title;
                this.content = content;
        }
}
