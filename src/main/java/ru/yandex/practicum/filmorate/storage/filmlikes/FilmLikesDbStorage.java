package ru.yandex.practicum.filmorate.storage.filmlikes;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FilmLikesRepository;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class FilmLikesDbStorage implements FilmLikesStorage {
    final FilmLikesRepository filmLikesRepository;

    @Override
    public List<FilmLike> findAll() {
        return filmLikesRepository.findAll();
    }

    @Override
    public void create(long userId, long filmId) {
        filmLikesRepository.create(userId, filmId);
    }

    @Override
    public void remove(long userId, long filmId) {
        filmLikesRepository.remove(userId, filmId);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(ExceptionMessages.CLEAN_DB_TABLE_NOT_SUPPORTED);
    }
}
