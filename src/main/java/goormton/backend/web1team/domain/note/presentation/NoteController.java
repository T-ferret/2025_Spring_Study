package goormton.backend.web1team.domain.note.presentation;

import goormton.backend.web1team.domain.note.dto.CreateNoteRequest;
import goormton.backend.web1team.domain.note.dto.NoteResponse;
import goormton.backend.web1team.domain.note.service.NoteService;
import goormton.backend.web1team.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "노트 컨트롤러 API", description = "노트 CRUD를 위한 컨트롤러.")
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "노트 추가", description = "노트를 하나 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCustom<?> createNote(@Valid @RequestBody CreateNoteRequest request) {
        NoteResponse response = noteService.createNote(request);

        return ResponseCustom.CREATED(response);
    }

    @Operation(summary = "노트 리스트 조회", description = "모든 노트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NoteResponse.class))
                    )
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping
    public ResponseCustom<?> getAllNotes() {
        List<NoteResponse> responses = noteService.getAllNotes();

        return ResponseCustom.OK(responses);
    }

    @Operation(summary = "노트 정보 조회", description = "어떤 노트 객체의 정보 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/{id}")
    public ResponseCustom<?> getNoteById(@PathVariable Integer id) {
        NoteResponse response = noteService.getNoteById(id);

        return ResponseCustom.OK(response);
    }

    @Operation(summary = "노트 업데이트", description = "어떤 노트 객체 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PutMapping("/{id}")
    public ResponseCustom<?> updateNote(@PathVariable Integer id, @Valid @RequestBody CreateNoteRequest request) {
        NoteResponse response = noteService.updateNote(id, request);

        return ResponseCustom.OK(response);
    }

    @Operation(summary = "노트 삭제", description = "어떤 노트 객체를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @DeleteMapping("/{id}")
    public ResponseCustom<?> deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return ResponseCustom.OK();
    }
}
