package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film findById(Long fId);

    Collection<Film> findAll();

    Film create(Film film);

    Film update(Film newFilm);

    Collection<Film> getPopular(Long count);

    void addLike(Film film, Long userId);

    void removeLike(Film film, Long userId);
}
