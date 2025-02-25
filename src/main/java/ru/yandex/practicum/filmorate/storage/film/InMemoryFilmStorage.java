package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLike;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.filmlikes.InMemoryFilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.genre.InMemoryGenreStorage;
import ru.yandex.practicum.filmorate.storage.mparating.InMemoryMpaRatingStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final InMemoryFilmLikesStorage filmLikesStorage;
    private final InMemoryGenreStorage genreStorage;
    private final InMemoryMpaRatingStorage mpaRatingStorage;

    @Override
    public Optional<Film> findById(Long fId) {
        return films.get(fId) == null ? Optional.empty() : Optional.of(films.get(fId));
    }

    @Override
    public List<Film> findAll() {
        return films.values().stream().toList();
    }

    @Override
    public Film create(Film film) {
        fillRefNames(film);

        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film newFilm) {
        fillRefNames(newFilm);

        films.put(newFilm.getId(), newFilm);

        return newFilm;
    }

    @Override
    public List<Film> getPopular(int count) {
        Map<Long, Long> likedFilms = new HashMap<>();
        films.values().forEach(film -> likedFilms.put(film.getId(), 0L));

        likedFilms.putAll(filmLikesStorage.findAll().stream()
                .collect(Collectors.groupingBy(FilmLike::getFilmId, Collectors.counting())));

        return likedFilms.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(count)
                .map(Map.Entry::getKey)
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private void fillRefNames(Film film) {
        if (film.getGenres() == null) {
            film.setGenres(new ArrayList<>());
        } else {
            film.getGenres().forEach(genre -> {
                Optional<Genre> filmGenre = genreStorage.getById(genre.getId());
                filmGenre.ifPresent(v -> genre.setName(v.getName()));
            });
        }

        if (film.getMpa() != null) {
            Optional<MpaRating> mpaRating = mpaRatingStorage.getById(film.getMpa().getId());
            mpaRating.ifPresent(v -> film.getMpa().setName(v.getName()));
        }
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
