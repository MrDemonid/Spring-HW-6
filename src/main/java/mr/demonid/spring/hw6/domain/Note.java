package mr.demonid.spring.hw6.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notes")
@Schema(description = "Личная заметка")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    @Schema(description = "Название заметки")
    @NotBlank
    @Size(min = 1, max = 64)
    private String title;

    @Column(nullable = false, length = 1024)
    @Schema(description = "Тело заметки")
    @NotBlank
    @Size(min = 1, max = 1024)
    private String body;

    @Schema(description = "Дата создания заметки")
    private LocalDateTime created;
}
