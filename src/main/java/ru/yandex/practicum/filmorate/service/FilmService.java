package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage fStorage;
    private final UserStorage uStorage;

    public Collection<Film> findAll() {
        return fStorage.findAll();
    }

    public Film create(Film film) {
        return fStorage.create(film);
    }

    public Film update(Film film) {
        return fStorage.update(film);
    }

    public Collection<Film> getPopular(Long count) {
        return fStorage.getPopular(count);
    }

    public void addLike(Long filmId, Long userId) {
        Film film = Optional.ofNullable(fStorage.findById(filmId))
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.FILM_NOT_FOUND, filmId)));
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));

        fStorage.addLike(film, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = Optional.ofNullable(fStorage.findById(filmId))
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.FILM_NOT_FOUND, filmId)));
        User user = Optional.ofNullable(uStorage.findById(userId))
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId)));

        fStorage.removeLike(film, userId);
    }
}
