package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Optional<Film> findById(Long fId);

    List<Film> findAll();

    Film create(Film film);

    Film update(Film newFilm);

    List<Film> getPopular(int count);
}
