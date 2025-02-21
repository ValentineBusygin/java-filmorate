package ru.yandex.practicum.filmorate.storage.filmlikes;

import ru.yandex.practicum.filmorate.model.FilmLike;

import java.util.List;

public interface FilmLikesStorage {

    List<FilmLike> findAll();

    void create(long userId, long filmId);

    void remove(long userId, long filmId);

    void clear();
}
