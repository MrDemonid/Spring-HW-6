package mr.demonid.spring.hw6.services;

import lombok.AllArgsConstructor;
import mr.demonid.spring.hw6.domain.Note;
import mr.demonid.spring.hw6.exceptions.CreateNoteException;
import mr.demonid.spring.hw6.exceptions.DeleteNoteException;
import mr.demonid.spring.hw6.exceptions.UpdateNoteException;
import mr.demonid.spring.hw6.repository.NoteRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository repository;
    private CacheManager cacheManager;

    /**
     * Возвращает список всех заметок.
     */
    @Override
    public List<Note> getAllNotes() {
        printCacheState();                      // посмотрим что есть кэше (для отладки)
        return repository.findAll();
    }

    /**
     * Возвращает заметку с заданным ID.
     */
    @Cacheable(value = "notes", key = "#id", unless = "#result == null") // кешируем по id и только реальные данные
    @Override
    public Optional<Note> getNoteById(Long id) {
        System.out.println("getNoteById(" + id + ")");      // не вызовется если есть в кеше
        return repository.findById(id);
    }

    /**
     * Возвращает список заметок по заданному заголовку.
     * @param title Заголовок для отбора заметок.
     */
    @Cacheable(value = "notesByTitle", key = "#title", unless = "#result == null")  // кешируем по заголовкам и только реальные данные
    @Override
    public List<Note> getNotesByTitle(String title) {
        System.out.println("getNotesByTitle(" + title + ")");   // не вызовется если есть в кеше
        return repository.findNotesByTitle(title);
    }

    /**
     * Добавление новой заметки в БД.
     */
    @CachePut(value = "notes", key = "#note.id", unless = "#result == null")
    @Override
    public Note createNote(Note note) {
        if (note != null && note.getId() == 0)
        {
            return repository.save(note);
        }
        throw new CreateNoteException("Заметка уже существует или неправильно инициализирована!");
    }

    /**
     * Обновление данных заметки в БД.
     */
    @CachePut(value = "notes", key = "#note.id", unless = "#result == null")
    @Override
    public Note updateNote(Note note) {
        if (note != null && note.getId() != null)
        {
            Optional<Note> n = repository.findById(note.getId());
            if (n.isPresent()) {
                return repository.save(note);
            }
        }
        throw new UpdateNoteException("ID недействителен!");
    }

    /**
     * Удаление заметки из БД.
     */
    @CacheEvict(value = {"notes", "notesByTitle"}, key = "#id")
    @Override
    public void deleteNote(Long id) {
        if (id != null && id != 0)
        {
            Optional<Note> n = repository.findById(id);
            if (n.isPresent()) {
                repository.deleteById(id);
                return;
            }
        }
        throw new DeleteNoteException("ID недействителен!");
    }

    /**
     * Показывает состояние кэша
     */
    private void printCacheState() {
        Cache cache = cacheManager.getCache("notes");
        if (cache != null) {
            System.out.println("Элементы 'notes':");
            if (cache instanceof org.springframework.cache.concurrent.ConcurrentMapCache) {
                ConcurrentMap<?, ?> nativeCache = ((org.springframework.cache.concurrent.ConcurrentMapCache) cache).getNativeCache();
                nativeCache.forEach((key, value) -> System.out.println(key + " -> " + value));
            } else {
                System.out.println("Тип кэша не поддерживает прямой доступ к элементам");
            }
        } else {
            System.out.println("Не найден кэш 'notes'.");
        }
    }

}
