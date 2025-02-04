package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmServiceTests {

    @Test
    void getFilms() {
        FilmService fService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
        Film film = new Film();
        film.setName("film1");
        film.setDescription("film1desc");
        film.setReleaseDate(LocalDate.now().minusDays(120));
        film.setDuration(60L);
        Film createdFilm = fService.create(film);
        assertEquals(createdFilm.getId(), 1, "Ожидалось, что ID фильма будет 1");

        Collection<Film> fCollection = fService.findAll();
        assertEquals(fCollection.size(), 1, "Ожидалось, что в наборе будет один фильм");
        assertEquals(createdFilm, fCollection.stream().findFirst().get(), "Ожидалось, что фильм в массиве совпадет с созданным");
    }

    @Test
    void updateFilm() {
        FilmService fService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
        Film film = new Film();
        film.setName("film1");
        film.setDescription("film1desc");
        film.setReleaseDate(LocalDate.now().minusDays(120));
        film.setDuration(60L);
        Film createdFilm = fService.create(film);
        assertEquals(createdFilm.getId(), 1, "Ожидалось, что ID фильма будет 1");

        Collection<Film> fCollection = fService.findAll();
        assertEquals(fCollection.size(), 1, "Ожидалось, что в наборе будет один фильм");
        assertEquals(createdFilm, fCollection.stream().findFirst().get(), "Ожидалось, что фильм в массиве совпадет с созданным");

        createdFilm.setDescription("film1descUpdated");
        Film updatedFilm = fService.update(createdFilm);

        fCollection = fService.findAll();
        assertEquals("film1descUpdated", fCollection.stream().findFirst().get().getDescription(), "Ожидалось, что описание будет обновлено");
    }
}
