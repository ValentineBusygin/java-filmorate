package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final Map<Long, Set<Long>> likes = new HashMap<>();

    @Override
    public Film findById(Long fId) {
        return films.get(fId);
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(Film film) {
        log.info("Добавляется фильм {}", film);

        if (film.getDescription().length() > 200) {
            log.error("Описание фильма слишком длинное");

            throw new ValidationException("Название фильма не должно превышать 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("Релиз фильма раньше 28 декабря 1895 года");

            throw new ValidationException("Релиз фильма не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.error("Продолжительность фильма не положительная или не указана");

            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }

        film.setId(getNextId());

        films.put(film.getId(), film);
        likes.put(film.getId(), new HashSet<>());
        log.info("Добавлен фильм {}", film);

        return film;
    }

    @Override
    public Film update(Film newFilm) {
        log.info("Обновляется фильм {}", newFilm);

        if (newFilm.getId() == null) {
            log.error("Не указан Id обновляемого фильма");

            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());

            if (!newFilm.getName().equals(oldFilm.getName())) {
                oldFilm.setName(newFilm.getName());
            }
            if (newFilm.getDescription() != null) {
                oldFilm.setDescription(newFilm.getDescription());
            }
            if (newFilm.getReleaseDate() != null) {
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            if (newFilm.getDuration() != null) {
                oldFilm.setDuration(newFilm.getDuration());
            }

            log.info("Обновлен фильм {}", oldFilm);
            return oldFilm;
        }
        log.error("Фильм с id = {} не найден", newFilm.getId());

        throw new NotFoundException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    @Override
    public Collection<Film> getPopular(Long count) {
        return films.values().stream()
                .sorted((f1, f2) -> {
                    int f1Likes = likes.get(f1.getId()).size();
                    int f2Likes = likes.get(f2.getId()).size();
                    if (f1Likes != f2Likes) {
                        return f2Likes - f1Likes;
                    } else {
                        return f1.getId().compareTo(f2.getId());
                    }
                })
                .limit(count)
                .toList();
    }

    @Override
    public void addLike(Film film, Long userId) {
        Set<Long> fLikes = likes.get(film.getId());
        fLikes.add(userId);
    }

    @Override
    public void removeLike(Film film, Long userId) {
        Set<Long> fLikes = likes.get(film.getId());
        fLikes.remove(userId);
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
