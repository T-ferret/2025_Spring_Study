package goormton.backend.web1team;

import com.fasterxml.jackson.databind.ObjectMapper;
import goormton.backend.web1team.domain.note.application.NoteService;
import goormton.backend.web1team.domain.note.domain.Note;
import goormton.backend.web1team.domain.note.dto.CreateNoteRequest;
import goormton.backend.web1team.domain.note.presentation.NoteController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NoteService noteService;

    @Test
    public void testCreateNote() throws Exception {
        // given
        CreateNoteRequest request = CreateNoteRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        Note note = Note.builder()
                .id(1)
                .title(request.title())
                .content(request.content())
                .build();

        // 서비스의 createNote 메서드 호출 시 미리 정의한 Note 객체 반환
        Mockito.when(noteService.createNote(any(CreateNoteRequest.class))).thenReturn(note);

        // when & then : ResponseCustom.CREATED() 반환, HTTP 201, data 필드에 NoteResponse 내용 포함
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(note.getId()))
                .andExpect(jsonPath("$.data.title").value(note.getTitle()))
                .andExpect(jsonPath("$.data.content").value(note.getContent()))
                .andExpect(jsonPath("$.statusCode").value(201))
        ;
    }

    @Test
    public void testGetAllNotes() throws Exception {
        Note note = Note.builder()
                .id(1)
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        List<Note> notes = List.of(note);
        Mockito.when(noteService.getAllNotes()).thenReturn(notes);

        // when & then : ResponseCustom.OK() 반환, HTTP 200, data 필드가 List 형태임
        mockMvc.perform(get("/api/notes")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(note.getId()))
                .andExpect(jsonPath("$.data[0].title").value(note.getTitle()))
                .andExpect(jsonPath("$.data[0].content").value(note.getContent()))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    public void testGetNoteById() throws Exception {
        Note note = Note.builder()
                .id(1)
                .title("테스트 제목")
                .content("테스트 내용")
                .build();

        Mockito.when(noteService.getNoteById(eq(1))).thenReturn(note);

        // when & then : 특정 ID의 메모 조회 테스트
        mockMvc.perform(get("/api/notes/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(note.getId()))
                .andExpect(jsonPath("$.data.title").value(note.getTitle()))
                .andExpect(jsonPath("$.data.content").value(note.getContent()))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    public void testUpdateNote() throws Exception {
        CreateNoteRequest request = CreateNoteRequest.builder()
                .title("업데이트")
                .content("업데이트 내용")
                .build();

        Note note = Note.builder()
                .id(1)
                .title(request.title())
                .content(request.content())
                .build();

        Mockito.when(noteService.updateNote(eq(1), any(CreateNoteRequest.class))).thenReturn(note);

        // when & then : ResponseCustom.OK() 반환, HTTP 200, 업데이트된 data 검증
        mockMvc.perform(put("/api/notes/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(note.getId()))
                .andExpect(jsonPath("$.data.title").value(note.getTitle()))
                .andExpect(jsonPath("$.data.content").value(note.getContent()))
                .andExpect(jsonPath("$.statusCode").value(200));
    }

    @Test
    public void testDeleteNote() throws Exception {
        // deleteNote 메서드는 void 반환. 별도의 data 없이 ResponseCustom.OK() 사용
        // when & then
        mockMvc.perform(delete("/api/notes/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(nullValue()))
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
