package goormton.backend.web1team.domain.note.domain;

import goormton.backend.web1team.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;

    @Builder
    public Note(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
