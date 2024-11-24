package mr.demonid.spring.hw6.services;

import lombok.AllArgsConstructor;
import mr.demonid.spring.hw6.domain.Note;
import mr.demonid.spring.hw6.repository.NoteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository repository;

    /**
     * Возвращает список всех заметок.
     */
    @Override
    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    /**
     * Возвращает заметку с заданным ID.
     */
    @Cacheable(value = "notes", unless = "#result == null ")                    // кешируем по id и только реальные данные
    @Override
    public Optional<Note> getNoteById(Long id) {
        return repository.findById(id);
    }

    /**
     * Возвращает список заметок по заданному заголовку.
     * @param title Заголовок для отбора заметок.
     */
    @Cacheable(value = "notes", key = "#title", unless = "#result == null ")    // кешируем по заголовкам и только реальные данные
    @Override
    public List<Note> getNotesByTitle(String title) {
        return repository.findNotesByTitle(title);
    }

    /**
     * Добавление новой заметки в БД.
     */
    @CachePut(value = "notes", key = "#note.id", unless = "#result == null")
    @Override
    public Note createNote(Note note) {
        return repository.save(note);
    }

    /**
     * Обновление данных заметки в БД.
     */
    @CachePut(value = "notes", key = "#note.id", unless = "#result == null")
    @Override
    public Note updateNote(Note note) {
        return repository.save(note);
    }

    /**
     * Удаление заметки из БД.
     */
//    @CacheEvict(value = "notes", key = "#note.id")      // в кэше остаются ключи по title
    @CacheEvict(value = "notes", allEntries = true)     // полностью очищает кэш (TODO: узнать как очистить только по ключам)
    @Override
    public void deleteNote(Note note) {
        repository.delete(note);
    }
}
