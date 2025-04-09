package goormton.backend.web1team.domain.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "노틑 객체 생성용 requestDTO입니다.")
public record CreateNoteRequest(
        @NotEmpty(message = "제목은 필수 입력 값입니다.")
        @Size(min = 3, max = 100, message = "최소 3, 최대 100자까지 입력 가능합니다.")
        String title,

        @NotEmpty(message = "내용은 필수 입력 값입니다.")
        String content
) {
}
