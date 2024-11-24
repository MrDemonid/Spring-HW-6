package mr.demonid.spring.hw6.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import mr.demonid.spring.hw6.domain.Note;
import mr.demonid.spring.hw6.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Tag(name = "Note controller", description = "REST API для управления заметками")
@RestController
@AllArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;

    /**
     * Возвращает список из всех заметок.
     */
    @Operation(summary = "Получить заметки", description = "Возвращает все имеющиеся в БД заметки. Не кэшируется.")
    @GetMapping
    ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    /**
     * Возвращает конкретную заметку по её ID.
     * @param id Идентификатор заметки.
     */
    @Operation(summary = "Поиск заметки", description = "Ищет и возвращает заметку по её ID. Кэшируемая операция.")
    @GetMapping("/{id}")
    ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> n = noteService.getNoteById(id);
        return n.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Возвращает все заметки с заданным заголовком.
     * @param title Заголовок (строка)
     */
    @Operation(summary = "Поиск заметок", description = "Ищет все заметки по заданному заголовку. Кешируется по заголовкам.")
    @GetMapping("/find")
    ResponseEntity<List<Note>> getNotesByTitle(
            @Parameter(description = "Строка заголовка.")
            @RequestParam String title) {
        List<Note> notes = noteService.getNotesByTitle(title);
        if (notes != null) {
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Добавление новой заметки.
     * @param note Новая заметка.
     * @return
     */
    @Operation(summary = "Добавление заметки.", description = "Добавляет в БД новую заметку. Кэшируемая операция.")
    @PostMapping
    ResponseEntity<Note> createNote(@RequestBody Note note) {
        note.setCreated(LocalDateTime.now());
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.CREATED);
    }

    /**
     * Обновляет данные заметки.
     * @param note Заметка с новыми данными.
     */
    @Operation(summary = "Изменение заметки", description = "Обновляет в БД данные о заметке. Кэшируемая операция.")
    @PutMapping
    ResponseEntity<Note> updateNote(
            @Parameter(description = "Заметка с обновленными полями.")
            @RequestBody Note note) {
        return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.ACCEPTED);
    }

    /**
     * Удаляет заметку.
     * @param id Идентификатор заметки.
     * @return
     */
    @Operation(summary = "Удаление заметки", description = "Удаляет заметку из БД. Полностью сбрасывает кэш.")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

}
