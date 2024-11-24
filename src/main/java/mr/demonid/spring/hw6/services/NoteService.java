package mr.demonid.spring.hw6.services;

import mr.demonid.spring.hw6.domain.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    /**
     * Получение списка всех заметок в БД.
     */
    List<Note> getAllNotes();

    Optional<Note> getNoteById(Long id);

    List<Note> getNotesByTitle(String title);

    /**
     * Добавление новой заметки в БД.
     */
    Note createNote(Note note);

    /**
     * Изменение существующей заметки.
     */
    Note updateNote(Note note);

    /**
     * Удаление заметки.
     */
    void deleteNote(Long id);

}
