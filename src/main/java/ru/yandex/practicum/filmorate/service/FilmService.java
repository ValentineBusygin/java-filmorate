package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.filmlikes.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mparating.MpaRatingStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage fStorage;
    private final UserStorage uStorage;
    private final MpaRatingStorage mStorage;
    private final GenreStorage gStorage;
    private final FilmLikesStorage fLikesStorage;

    public List<Film> findAll() {
        return fStorage.findAll();
    }

    public Film findById(Long id) {
        return fStorage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessages.FILM_NOT_FOUND, id)));
    }

    public Film create(Film film) {
        validate(film);

        Film newFilm = fStorage.create(film);

        log.info("Создан фильм {}", newFilm);

        return newFilm;
    }

    public Film update(Film film) {
        if (film.getId() == null) {
            log.error("Не указан id фильма");

            throw new ConditionsNotMetException("Не указан id фильма");
        }

        Optional<Film> oldFilmOp = fStorage.findById(film.getId());

        if (oldFilmOp.isPresent()) {
            validate(film);
            Film currFilm = fStorage.update(film);
            log.info("Обновлен фильм {}", currFilm);

            return currFilm;
        } else {
            log.error("Фильм с id {} не найден", film.getId());

            throw new NotFoundException(String.format(ExceptionMessages.FILM_NOT_FOUND, film.getId()));
        }
    }

    public List<Film> getPopular(int count) {
        return fStorage.getPopular(count);
    }

    public void addLike(Long filmId, Long userId) {
        checkFilmForExists(filmId);
        checkUserForExists(userId);

        fLikesStorage.create(userId, filmId);

        log.info("Фильму {} добавлен лайк пользователя {}", filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        checkFilmForExists(filmId);
        checkUserForExists(userId);

        fLikesStorage.remove(filmId, userId);

        log.info("У фильма {} удален лайк пользователя {}", filmId, userId);
    }

    private void checkFilmForExists(Long filmId) {
        fStorage.findById(filmId)
                .orElseThrow(() -> {
                    log.error("Фильм с id {} не найден", filmId);
                    return new NotFoundException(String.format(ExceptionMessages.FILM_NOT_FOUND, filmId));
                });
    }

    private void checkUserForExists(Long userId) {
        uStorage.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с id {} не найден", userId);
                    return new NotFoundException(String.format(ExceptionMessages.USER_NOT_FOUND, userId));
                });
    }

    private void validate(Film film) {
        List<Film> films = fStorage.findAll();
        if (films.stream().anyMatch(f -> f.equals(film))) {
            log.error("Фильм с названием {} и датой релиза {} уже существует", film.getName(), film.getReleaseDate());
            throw new ValidationException("Фильм с таким названием и датой релиза уже существует");
        }

        if (film.getReleaseDate().isBefore(Film.CINEMA_BIRTH_DAY)) {
            log.error("Дата релиза раньше {}", Film.CINEMA_BIRTH_DAY);
            throw new ValidationException(String.format(ExceptionMessages.INVALID_RELEASE_DATE, Film.CINEMA_BIRTH_DAY));
        }

        if (film.getMpa() != null) {
            Optional<MpaRating> mpaRating = mStorage.getById(film.getMpa().getId());
            if (mpaRating.isEmpty()) {
                log.error("Рейтинг с id {} не найден", film.getMpa().getId());
                throw new NotFoundException(String.format(ExceptionMessages.MPA_RATING_NOT_FOUND, film.getMpa().getId()));
            }
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            film.setGenres(film.getGenres().stream().distinct().toList());
            List<Genre> absentGenres = film.getGenres().stream()
                    .filter(g -> gStorage.getById(g.getId()).isEmpty())
                    .toList();
            if (!absentGenres.isEmpty()) {
                log.error("Фильм содержит отсутствующие в базе id жанров {}", absentGenres);
                throw new NotFoundException(String.format(ExceptionMessages.GENRE_NOT_FOUND, absentGenres));
            }
        }
    }

}
