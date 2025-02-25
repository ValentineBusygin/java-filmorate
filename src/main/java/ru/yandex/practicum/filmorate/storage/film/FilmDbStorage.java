package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repository.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    final FilmRepository filmRepository;

    @Override
    public Optional<Film> findById(Long fId) {
        return filmRepository.findById(fId);
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film create(Film film) {
        return filmRepository.create(film);
    }

    @Override
    public Film update(Film newFilm) {
        return filmRepository.update(newFilm);
    }

    @Override
    public List<Film> getPopular(int count) {
        return filmRepository.findPopular(count);
    }
}
