package ru.yandex.practicum.filmorate.controller.mparating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.controller.MpaRatingController;
import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class MpaRatingControllerTest {
    @Autowired
    MpaRatingController mpaRatingController;

    @Test
    void genreControllerFindsGenreById() {
        MpaRating mpaRating = mpaRatingController.findById(1L);
        assertEquals("G", mpaRating.getName(), "Контроллер нашел не тот рейтинг по Id");
    }

    @Test
    void genreControllerFindAll() {
        List<MpaRating> mpaRatings = mpaRatingController.findAll();
        assertEquals(5, mpaRatings.size(), "Контроллер не нашел все жанры");
    }
}
